#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# --- Configuration ---
CSV_FILE="SyncLog.csv"
MAIN_BRANCH="origin/main" # Branch to get BevyBeats specific files from
UPSTREAM_BRANCH="upstream/jetpack_compose" # Just for reference

# --- Pre-checks ---
if [ ! -f "$CSV_FILE" ]; then
  echo "Error: Sync log file not found: $CSV_FILE"
  exit 1
fi

if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
 echo "Error: Not inside a Git repository."
 exit 1
fi

current_branch=$(git rev-parse --abbrev-ref HEAD)
# Warning if running on main, allow user to continue if intended
# if [[ "$current_branch" == "main" ]]; then
#   read -p "Warning: You are on the main branch. Proceed with caution? (y/N) " -n 1 -r
#   echo
#   if [[ ! $REPLY =~ ^[Yy]$ ]]; then
#     exit 1
#   fi
# fi

echo "Starting sync process based on $CSV_FILE..."
echo "Ensure you are on the correct branch and have checked out $UPSTREAM_BRANCH content."
# Optional: Add a sleep or prompt here if needed

# --- Phase 1: Rename Directories ---
echo "Phase 1: Renaming package directories..."

paths_to_rename=(
  "app/src/main/java"
  "app/src/test/java"
  "app/src/androidTest/java"
  "kotlinYtmusicScraper/src/main/java"
  "kotlinYtmusicScraper/src/test/java"
  "kotlinYtmusicScraper/src/androidTest/java"
  "lyricsProviders/src/main/java"
  "lyricsProviders/src/test/java"
  "lyricsProviders/src/androidTest/java"
  "spotify/src/main/java"
  "spotify/src/test/java"
  "spotify/src/androidTest/java"
)

for base_path in "${paths_to_rename[@]}"; do
  if [ -d "$base_path/com" ]; then
    old_pkg_path="$base_path/com/maxrave"
    new_pkg_base_path="$base_path/com/futureatoms" # Base dir for futureatoms

    if [ -d "$old_pkg_path" ]; then
      echo "  Checking rename for: $old_pkg_path"
      # Ensure the target parent directory exists
      mkdir -p "$new_pkg_base_path"

      # Rename 'maxrave' directory to 'futureatoms' only if 'futureatoms' doesn't exist yet
      if [ ! -d "$new_pkg_base_path/bevybeats" ] && [ ! -d "$new_pkg_base_path/spotify" ] && [ ! -d "$new_pkg_base_path/lyricsproviders" ] && [ ! -d "$new_pkg_base_path/kotlinytmusicscraper" ]; then
         # More specific rename based on actual content - assumes maxrave contains simpmusic, etc.
         if [ -d "$old_pkg_path/simpmusic" ]; then
             mv "$old_pkg_path/simpmusic" "$new_pkg_base_path/bevybeats"
             echo "    Renamed $old_pkg_path/simpmusic to $new_pkg_base_path/bevybeats"
         elif [ -d "$old_pkg_path/spotify" ]; then
             mv "$old_pkg_path/spotify" "$new_pkg_base_path/spotify"
             echo "    Renamed $old_pkg_path/spotify to $new_pkg_base_path/spotify"
         elif [ -d "$old_pkg_path/lyricsproviders" ]; then
              mv "$old_pkg_path/lyricsproviders" "$new_pkg_base_path/lyricsproviders"
              echo "    Renamed $old_pkg_path/lyricsproviders to $new_pkg_base_path/lyricsproviders"
         elif [ -d "$old_pkg_path/kotlinytmusicscraper" ]; then
                mv "$old_pkg_path/kotlinytmusicscraper" "$new_pkg_base_path/kotlinytmusicscraper"
                echo "    Renamed $old_pkg_path/kotlinytmusicscraper to $new_pkg_base_path/kotlinytmusicscraper"
         else
             # Generic rename if specific subdirs aren't found (less likely needed)
              # mv "$old_pkg_path" "$new_pkg_base_path" # This might be too broad
              echo "    Warning: Could not find specific subdirectory (simpmusic, spotify, etc.) within $old_pkg_path. Manual move might be needed."
         fi
         # Clean up empty maxrave parent if possible
         rmdir "$old_pkg_path" 2>/dev/null || true
      else
        echo "    Warning: Target directory structure under $new_pkg_base_path seems to exist. Skipping rename for $old_pkg_path."
      fi
    fi
  fi
done

echo "Staging potential directory renames..."
# Use git add -A to stage moves/renames/deletions robustly
git add -A .

# --- Phase 2: Process Files based on CSV ---
echo ""
echo "Phase 2: Processing individual files from $CSV_FILE..."

# Determine sed in-place edit flag
sed_inplace_flag="-i"
if [[ $(uname) == "Darwin" ]]; then
  sed_inplace_flag="-i ''" # macOS sed requires an argument for -i
fi

apply_replacements() {
    local target_file="$1"
    echo "    Applying replacements to $target_file"
    if [[ ! -f "$target_file" ]]; then
        echo "    Warning: Cannot apply replacements, file not found: $target_file"
        return 1 # Indicate failure
    fi

    # Handle different sed versions/flags if necessary
    # Note: Basic sed doesn't handle case variations like "SimpMusic" vs "simpmusic" elegantly in one go.
    # This applies the specific replacements requested. Add more sed commands if needed.
    eval "sed $sed_inplace_flag 's/com\.maxrave\.simpmusic/com.futureatoms.bevybeats/g' \"\$target_file\""
    eval "sed $sed_inplace_flag 's/com\.maxrave/com.futureatoms/g' \"\$target_file\""
    eval "sed $sed_inplace_flag 's/maxrave-dev/futureatoms/g' \"\$target_file\""
    eval "sed $sed_inplace_flag 's/SimpMusic/BevyBeats/g' \"\$target_file\""
    eval "sed $sed_inplace_flag 's/simpmusic/bevybeats/g' \"\$target_file\"" # Lowercase too
    # Add other specific replacements here if needed based on CSV 'extra steps'

    return 0 # Indicate success
}

# Read CSV, skipping header, handle potential Windows line endings (\r)
tail -n +2 "$CSV_FILE" | tr -d '\r' | while IFS=, read -r file_path decision extra_steps || [[ -n "$file_path" ]]; do
  # Trim leading/trailing whitespace (optional but safer)
  file_path=$(echo "$file_path" | awk '{$1=$1};1')
  decision=$(echo "$decision" | awk '{$1=$1};1')
  extra_steps=$(echo "$extra_steps" | awk '{$1=$1};1')


  if [[ -z "$file_path" ]]; then
    echo "Skipping row with empty file path."
    continue
  fi

  echo "Processing: $file_path | Decision: $decision"

  if [[ "$decision" == "BevyBeats" ]]; then
    echo "  Action: Checking out from $MAIN_BRANCH"
    # Use git show to check if file exists in main before checkout to avoid error flood
    if git show "$MAIN_BRANCH":"$file_path" > /dev/null 2>&1; then
        git checkout "$MAIN_BRANCH" -- "$file_path"
        git add "$file_path" # Stage the checked-out version
    else
        echo "  Warning: File '$file_path' not found in $MAIN_BRANCH. Skipping checkout."
        # Should we delete the upstream version if it exists? Depends on intent.
        # if [ -f "$file_path" ]; then
        #   echo "  Note: Upstream version exists but BevyBeats version not found. Keeping upstream for now."
        # fi
    fi

  elif [[ "$decision" == "Upstream" || "$decision" == "merge" ]]; then
    echo "  Action: Keeping upstream/merged version."
    if [[ "$extra_steps" == *"replace"* ]]; then
      if apply_replacements "$file_path"; then
         git add "$file_path" # Stage the modified version
      fi
    elif [ -f "$file_path" ]; then
         # Ensure file is staged even if no replacements needed
         git add "$file_path"
    else
        echo "  Warning: File $file_path not found for Upstream/Merge decision."
    fi
    if [[ "$decision" == "merge" ]]; then
      echo "  MANUAL REVIEW REQUIRED for merge: $file_path"
    fi

  elif [[ "$decision" == "discard" ]]; then
    echo "  Action: Discarding"
    if [[ -f "$file_path" ]]; then
      git rm --ignore-unmatch --cached "$file_path" > /dev/null 2>&1 # Remove from index if tracked, quiet
      rm -f "$file_path" # Remove from working dir
      echo "    Removed file: $file_path"
    else
      echo "    File not found, nothing to discard: $file_path"
      # Also remove from index if it was only staged for deletion previously
      git rm --ignore-unmatch --cached "$file_path" > /dev/null 2>&1
    fi
    # `git add -A .` might handle the removal staging, but explicit git rm is safer

  else
    echo "  Warning: Unknown decision '$decision' for file: $file_path"
  fi

done

# --- Final Steps ---
echo ""
echo "Sync script finished."
echo "Please review the changes carefully using 'git status' and 'git diff HEAD'."
echo "Stage any remaining untracked files if necessary (e.g., using 'git add .')."
echo "Commit the changes to your branch '$current_branch' if satisfied."
echo "Remember to build and test before merging to main!"
