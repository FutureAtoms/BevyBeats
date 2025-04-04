package com.futureatoms.bevybeats.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.window.DialogProperties
import com.futureatoms.bevybeats.R
import com.futureatoms.bevybeats.ui.theme.seed
import com.futureatoms.bevybeats.ui.theme.typo

@Composable
@ExperimentalMaterial3Api
fun ReviewDialog(
    onDismissRequest: () -> Unit,
    onDoneReview: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    AlertDialog(
        properties =
            DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
        onDismissRequest = {
            onDismissRequest.invoke()
        },
        confirmButton = {
            TextButton(onClick = {
                onDoneReview.invoke()
                onDismissRequest.invoke()
                uriHandler.openUri("https://github.com/futureatoms/BevyBeats")
            }) {
                Text(
                    stringResource(R.string.give_a_star),
                    style = typo.bodySmall,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest.invoke()
            }) {
                Text(
                    stringResource(id = R.string.later),
                    style = typo.bodySmall,
                )
            }
        },
        icon = {
            Icon(painterResource(R.drawable.mono), "App Icon")
        },
        title = {
            Text(
                stringResource(R.string.enjoying_bevybeats),
                style = typo.labelSmall,
            )
        },
        text = {
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.if_you_enjoy_using_bevybeats_star_bevybeats_on_github_or_leave_a_review_on))
                    withLink(
                        LinkAnnotation.Url(
                            "https://www.producthunt.com/products/bevybeats",
                            TextLinkStyles(style = SpanStyle(textDecoration = TextDecoration.Underline, color = seed)),
                        ) {
                            onDoneReview.invoke()
                            onDismissRequest.invoke()
                            uriHandler.openUri("https://www.producthunt.com/products/bevybeats")
                        },
                    ) {
                        append(" ProductHunt")
                    }
                },
                textAlign = TextAlign.Center,
                style = typo.bodySmall,
            )
        },
    )
}