package com.futureatoms.bevybeats.utils

import android.content.Context
import android.util.Log

object VersionManager {

    private var versionName: String? = null

    fun initialize(context: Context) {
        if (versionName == null) {
            versionName = try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (_: Exception) {
                String()
            }
        }
    }


    fun getVersionName(): String {
        return removeDevSuffix(versionName ?: String())
    }

    private fun removeDevSuffix(versionName: String): String {
        // Keep removing prefix like 'v' before comparing
        val cleanedVersion = versionName.removePrefix("v").replace("-dev", "")
        return cleanedVersion
    }

    /**
     * Compares two version strings (e.g., "1.3.0", "v1.4.1-beta").
     * Returns true if versionToCheck is strictly newer than currentVersion.
     */
    fun isVersionNewer(currentVersion: String?, versionToCheck: String?): Boolean {
        if (currentVersion.isNullOrBlank() || versionToCheck.isNullOrBlank()) {
            return false // Cannot compare if either is missing
        }

        val currentClean = removeDevSuffix(currentVersion)
        val toCheckClean = removeDevSuffix(versionToCheck)

        if (currentClean == toCheckClean) {
            return false // Versions are identical
        }

        val currentParts = currentClean.split(".").mapNotNull { it.toIntOrNull() }
        val toCheckParts = toCheckClean.split(".").mapNotNull { it.toIntOrNull() }

        val maxParts = maxOf(currentParts.size, toCheckParts.size)

        for (i in 0 until maxParts) {
            val currentPart = currentParts.getOrElse(i) { 0 }
            val toCheckPart = toCheckParts.getOrElse(i) { 0 }

            if (toCheckPart > currentPart) {
                return true // Found a newer part
            }
            if (toCheckPart < currentPart) {
                return false // Found an older part, so it's not newer
            }
            // If parts are equal, continue to the next part
        }

        return false // All parts were equal, should have been caught by the initial equality check, but return false just in case.
    }

}