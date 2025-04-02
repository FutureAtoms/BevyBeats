#!/usr/bin/env python3
import os
import re
import sys
from pathlib import Path

def case_preserving_replace(text, old_word, new_word):
    """Replace old_word with new_word in text, preserving case."""
    def replace_match(match):
        matched_text = match.group(0)
        
        # If all uppercase
        if matched_text.isupper():
            return new_word.upper()
        # If first letter capitalized (Title case)
        elif matched_text[0].isupper() and matched_text[1:].islower():
            return new_word[0].upper() + new_word[1:].lower()
        # If all lowercase
        elif matched_text.islower():
            return new_word.lower()
        # Mixed case - try to match character by character
        else:
            result = ""
            for i, char in enumerate(matched_text):
                if i < len(new_word):
                    if char.isupper():
                        result += new_word[i].upper()
                    else:
                        result += new_word[i].lower()
                else:
                    break
            # If new_word is longer than matched_text, append the rest in lowercase
            if len(new_word) > len(matched_text):
                result += new_word[len(matched_text):].lower()
            return result
    
    # Process the text multiple times with different patterns
    result = text
    
    # Standard word boundary version
    pattern1 = r'\b{}\b'.format(re.escape(old_word))
    regex1 = re.compile(pattern1, re.IGNORECASE)
    result = regex1.sub(replace_match, result)
    
    # Version without word boundaries for catching things like "bevybeats_"
    pattern2 = r'{}(?=[-_\.])'.format(re.escape(old_word))
    regex2 = re.compile(pattern2, re.IGNORECASE)
    result = regex2.sub(replace_match, result)
    
    # Version for catching cases at the end of strings
    pattern3 = r'{}$'.format(re.escape(old_word))
    regex3 = re.compile(pattern3, re.IGNORECASE)
    result = regex3.sub(replace_match, result)
    
    # Version for catching lowercase variants
    pattern4 = r'\b{}\b'.format(re.escape(old_word.lower()))
    regex4 = re.compile(pattern4, re.IGNORECASE)
    result = regex4.sub(replace_match, result)
    
    # Version for variable-case variants
    pattern5 = r'\b{}\b'.format(re.escape(old_word.lower().capitalize()))
    regex5 = re.compile(pattern5, re.IGNORECASE)
    result = regex5.sub(replace_match, result)
    
    return result

def process_file_content(file_path):
    """Process the content of a file, replacing strings as specified."""
    try:
        # Skip binary files, common file types that might cause issues, and Git files
        file_extension = os.path.splitext(file_path)[1].lower()
        skip_extensions = ['.png', '.jpg', '.jpeg', '.gif', '.ico', '.mp3', '.mp4', '.zip', 
                          '.jar', '.apk', '.pdf', '.so', '.dll', '.exe', '.pack', '.idx']
        
        # Skip git files and directories
        if '.git' in str(file_path):
            return True
            
        if file_extension in skip_extensions:
            return True
            
        with open(file_path, 'r', encoding='utf-8', errors='ignore') as file:
            content = file.read()
        
        # Perform replacements
        new_content = content
        
        # Handle all variants of BevyBeats, including lowercase
        new_content = case_preserving_replace(new_content, "BevyBeats", "BevyBeats")
        new_content = case_preserving_replace(new_content, "bevybeats", "bevybeats")
        new_content = case_preserving_replace(new_content, "BEVYBEATS", "BEVYBEATS")
        
        # Also handle variations with underscores, hyphens, etc.
        new_content = case_preserving_replace(new_content, "bevy_beats", "bevy_beats")
        new_content = case_preserving_replace(new_content, "bevy-beats", "bevy-beats")
        
        # Replace futureatoms variants in order
        new_content = case_preserving_replace(new_content, "futureatoms", "futureatoms")
        new_content = case_preserving_replace(new_content, "futureatoms", "futureatoms")
        new_content = case_preserving_replace(new_content, "futureatoms", "futureatoms")
        
        # Also handle all lowercase/uppercase variations
        new_content = case_preserving_replace(new_content, "FUTUREATOMS", "FUTUREATOMS")
        new_content = case_preserving_replace(new_content, "FUTUREATOMS", "FUTUREATOMS")
        new_content = case_preserving_replace(new_content, "FUTUREATOMS", "FUTUREATOMS")
        
        # Write back the modified content if changes were made
        if new_content != content:
            with open(file_path, 'w', encoding='utf-8') as file:
                file.write(new_content)
            print(f"Updated content in {file_path}")
        return True
    except Exception as e:
        print(f"Error processing file content for {file_path}: {str(e)}")
        return False

def rename_all_paths(start_dir, no_confirm=False):
    """Rename all files and directories with replacements."""
    start_path = Path(start_dir).resolve()
    parent_path = start_path.parent
    
    # Check if the starting directory itself needs to be renamed
    start_dir_name = start_path.name
    new_start_dir_name = start_dir_name
    
    # Apply the replacements with all possible case variations
    new_start_dir_name = case_preserving_replace(new_start_dir_name, "BevyBeats", "BevyBeats")
    new_start_dir_name = case_preserving_replace(new_start_dir_name, "bevybeats", "bevybeats")
    new_start_dir_name = case_preserving_replace(new_start_dir_name, "BEVYBEATS", "BEVYBEATS")
    
    # Then handle futureatoms variants
    new_start_dir_name = case_preserving_replace(new_start_dir_name, "futureatoms", "futureatoms")
    new_start_dir_name = case_preserving_replace(new_start_dir_name, "futureatoms", "futureatoms")
    new_start_dir_name = case_preserving_replace(new_start_dir_name, "futureatoms", "futureatoms")
    
    rename_start_dir = False
    new_start_path = None
    if new_start_dir_name != start_dir_name:
        new_start_path = parent_path / new_start_dir_name
        rename_start_dir = True
        print(f"The starting directory will be renamed: {start_path} -> {new_start_path}")
    
    # 1. Process all file contents first
    print("Processing file contents...")
    for file_path in start_path.rglob('*'):
        if file_path.is_file():
            try:
                process_file_content(file_path)
            except Exception as e:
                print(f"Error processing {file_path}: {str(e)}")
    
    # 2. Collect all paths that need renaming
    files_to_rename = []
    dirs_to_rename = []
    
    # Process all files first
    for file_path in start_path.rglob('*'):
        if file_path.is_file():
            old_name = file_path.name
            new_name = old_name
            # Apply the replacements with all possible case variations
            new_name = case_preserving_replace(new_name, "BevyBeats", "BevyBeats")
            new_name = case_preserving_replace(new_name, "bevybeats", "bevybeats")
            new_name = case_preserving_replace(new_name, "BEVYBEATS", "BEVYBEATS")
            new_name = case_preserving_replace(new_name, "bevy_beats", "bevy_beats")
            new_name = case_preserving_replace(new_name, "bevy-beats", "bevy-beats")
            
            # Then handle futureatoms variants in order
            new_name = case_preserving_replace(new_name, "futureatoms", "futureatoms")
            new_name = case_preserving_replace(new_name, "futureatoms", "futureatoms")
            new_name = case_preserving_replace(new_name, "futureatoms", "futureatoms")
            
            # Also handle lowercase/uppercase variations
            new_name = case_preserving_replace(new_name, "FUTUREATOMS", "FUTUREATOMS")
            new_name = case_preserving_replace(new_name, "FUTUREATOMS", "FUTUREATOMS") 
            new_name = case_preserving_replace(new_name, "FUTUREATOMS", "FUTUREATOMS")
            
            if new_name != old_name:
                new_path = file_path.parent / new_name
                files_to_rename.append((file_path, new_path))
    
    # Then collect directories for renaming
    for dir_path in start_path.rglob('*'):
        if dir_path.is_dir() and dir_path != start_path:
            old_name = dir_path.name
            new_name = old_name
            # Apply the replacements with all possible case variations
            new_name = case_preserving_replace(new_name, "BevyBeats", "BevyBeats")
            new_name = case_preserving_replace(new_name, "bevybeats", "bevybeats")
            new_name = case_preserving_replace(new_name, "BEVYBEATS", "BEVYBEATS")
            new_name = case_preserving_replace(new_name, "bevy_beats", "bevy_beats")
            new_name = case_preserving_replace(new_name, "bevy-beats", "bevy-beats")
            
            # Then handle futureatoms variants in order
            new_name = case_preserving_replace(new_name, "futureatoms", "futureatoms")
            new_name = case_preserving_replace(new_name, "futureatoms", "futureatoms")
            new_name = case_preserving_replace(new_name, "futureatoms", "futureatoms")
            
            # Also handle lowercase/uppercase variations
            new_name = case_preserving_replace(new_name, "FUTUREATOMS", "FUTUREATOMS")
            new_name = case_preserving_replace(new_name, "FUTUREATOMS", "FUTUREATOMS")
            new_name = case_preserving_replace(new_name, "FUTUREATOMS", "FUTUREATOMS")
            
            if new_name != old_name:
                new_path = dir_path.parent / new_name
                dirs_to_rename.append((dir_path, new_path))
    
    # 3. Show summary and ask for confirmation
    print(f"\nFound {len(files_to_rename)} files to rename:")
    for old_path, new_path in files_to_rename:
        print(f"  {old_path} -> {new_path}")
    
    print(f"\nFound {len(dirs_to_rename)} directories to rename:")
    dirs_to_rename.sort(key=lambda x: len(x[0].parts), reverse=True)
    for old_path, new_path in dirs_to_rename:
        print(f"  {old_path} -> {new_path}")
    
    # Ask for confirmation if needed
    if not no_confirm and (files_to_rename or dirs_to_rename):
        confirm = input("\nDo you want to proceed with these renames? (y/n): ")
        if confirm.lower() != 'y':
            print("Operation cancelled.")
            return
    elif not (files_to_rename or dirs_to_rename):
        print("No files or directories to rename.")
        return
    
    # 4. Rename files
    print("\nRenaming files...")
    for old_path, new_path in files_to_rename:
        try:
            if old_path.exists():
                old_path.rename(new_path)
                print(f"Renamed file {old_path} to {new_path}")
        except Exception as e:
            print(f"Error renaming file {old_path}: {str(e)}")
    
    # 5. Rename directories from deepest to shallowest
    print("\nRenaming directories...")
    for old_path, new_path in dirs_to_rename:
        try:
            if old_path.exists():
                old_path.rename(new_path)
                print(f"Renamed directory {old_path} to {new_path}")
        except Exception as e:
            print(f"Error renaming directory {old_path}: {str(e)}")
    
    # 6. Rename the starting directory if needed
    if rename_start_dir and new_start_path:
        try:
            print(f"\nRenaming starting directory: {start_path} -> {new_start_path}")
            if not no_confirm:
                confirm = input("Do you want to rename the starting directory? (y/n): ")
                if confirm.lower() != 'y':
                    print("Starting directory not renamed.")
                    return
            
            start_path.rename(new_start_path)
            print(f"Successfully renamed starting directory to {new_start_path}")
            print(f"IMPORTANT: Your working directory has changed. You are now in {new_start_path.parent}")
        except Exception as e:
            print(f"Error renaming starting directory: {str(e)}")

if __name__ == "__main__":
    import argparse
    
    parser = argparse.ArgumentParser(description='Find and replace strings in files and directories.')
    parser.add_argument('directory', nargs='?', default='.', help='Starting directory (default: current directory)')
    parser.add_argument('--no-confirm', action='store_true', help='Skip confirmation prompt')
    parser.add_argument('--parent', action='store_true', help='Run from parent directory - useful if current directory needs renaming')
    
    args = parser.parse_args()
    
    directory = args.directory
    if args.parent:
        # If the parent flag is set, run from the parent directory
        print(f"Running from parent directory of {directory}")
        directory = str(Path(directory).resolve().parent)
    
    print(f"Starting find and replace operation from: {directory}")
    rename_all_paths(directory, args.no_confirm)
    print("Replacement process completed.")