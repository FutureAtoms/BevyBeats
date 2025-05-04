package com.futureatoms.bevybeats.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.futureatoms.bevybeats.R
import com.futureatoms.bevybeats.ui.theme.typo

@Composable
fun MandatoryLoginDialog(
    onLoginClick: () -> Unit
) {
    var animationScale by remember { mutableFloatStateOf(1f) }
    
    LaunchedEffect(key1 = true) {
        while(true) {
            kotlinx.coroutines.delay(1500)
            animationScale = 1.05f
            kotlinx.coroutines.delay(150)
            animationScale = 0.95f
            kotlinx.coroutines.delay(150)
            animationScale = 1.0f
        }
    }
    
    Dialog(
        onDismissRequest = { /* Cannot dismiss */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF242424),
            tonalElevation = 6.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.warning),
                    style = typo.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Box(modifier = Modifier.size(180.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.bevybot),
                        contentDescription = "Robot",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer(
                                scaleX = animationScale,
                                scaleY = animationScale
                            )
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(R.string.log_in_warning),
                    style = typo.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = SolidColor(Color(0xAAB6DFFF)),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color(0xCCB6DFFF),
                                shape = RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.go_to_log_in_page),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
} 