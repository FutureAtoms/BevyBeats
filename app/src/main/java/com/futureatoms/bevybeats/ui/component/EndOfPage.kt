package com.futureatoms.bevybeats.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.futureatoms.bevybeats.R
import com.futureatoms.bevybeats.ui.theme.typo
import com.futureatoms.bevybeats.utils.VersionManager

@Composable
fun EndOfPage() {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Text(
            "@2025 " + stringResource(R.string.app_name) + " " + stringResource(
                R.string.version_format,
                VersionManager.getVersionName()
            ) + "\nfutureatoms",
            style = typo.bodySmall,
            textAlign = TextAlign.Center,
            modifier =
            Modifier
                .padding(
                    top = 20.dp,
                )
                .alpha(0.8f),
        )
    }
}