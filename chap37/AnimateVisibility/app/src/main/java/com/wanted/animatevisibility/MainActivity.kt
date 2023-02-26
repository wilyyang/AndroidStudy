package com.wanted.animatevisibility

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wanted.animatevisibility.ui.theme.AnimateVisibilityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateVisibilityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    text: String, targetState: Boolean,
    onClick: (Boolean) -> Unit, bgColor: Color = Color.Blue
) {
    Button(
        modifier = Modifier.size(width = 100.dp, height = 50.dp),
        onClick = { onClick(targetState) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = bgColor,
            contentColor = Color.White
        )
    ) {
        Text(text)
    }
}

@Composable
fun MainScreen() {
    Column {
        Anim(
            enterAnim = fadeIn(animationSpec = tween(durationMillis = 5000))
                    + expandHorizontally(animationSpec = tween(durationMillis = 5000)),
            exitAnim = fadeOut(animationSpec = tween(durationMillis = 5000))
                    + shrinkHorizontally(animationSpec = tween(durationMillis = 5000)),
        )

        Anim(
            enterAnim = slideInHorizontally(
                animationSpec = tween(
                    durationMillis = 5000,
                    easing = LinearOutSlowInEasing
                )
            ),
            exitAnim = slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = 5000,
                    easing = CubicBezierEasing(0f, 1f, 0.5f, 1f)
                )
            ),
        )

        Anim(
            enterAnim = slideInHorizontally(
                animationSpec = repeatable(
                    3,
                    animation = tween(durationMillis = 1000),
                    repeatMode = RepeatMode.Reverse
                )
            ),
            exitAnim = slideOutHorizontally(
                animationSpec = repeatable(
                    3,
                    animation = tween(durationMillis = 1000),
                    repeatMode = RepeatMode.Reverse
                )
            ),
        )

        AnimChild()
    }
}

@Composable
fun Anim(enterAnim: EnterTransition, exitAnim: ExitTransition) {
    val state = remember { MutableTransitionState(false)}

    LaunchedEffect(Unit) {
        state.targetState = true
    }

    val onClick = { newState: Boolean ->
        state.targetState = newState
    }

    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Crossfade(
                targetState = state.targetState,
                animationSpec = tween(3000)
            ) { visible ->
                when (visible){
                    true -> CustomButton(text = "Hide", targetState = false, onClick = onClick, bgColor = Color.Red)
                    false -> CustomButton(text = "Show", targetState = true, onClick = onClick, bgColor = Color.Magenta)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(
            visibleState = state,
            enter = enterAnim,
            exit = exitAnim
        ) {
            Box(
                modifier = Modifier
                    .size(height = 100.dp, width = 200.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimChild(enterAnim: EnterTransition = EnterTransition.None,
              exitAnim: ExitTransition = ExitTransition.None) {
    var boxVisible by remember { mutableStateOf(true) }

    val onClick = { newState: Boolean ->
        boxVisible = newState
    }

    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

            Crossfade(
                targetState = boxVisible,
                animationSpec = tween(5000)
            ) { visible ->
                when (visible){
                    true -> CustomButton(text = "Hide", targetState = false, onClick = onClick, bgColor = Color.Red)
                    false -> CustomButton(text = "Show", targetState = true, onClick = onClick, bgColor = Color.Magenta)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(
            visible = boxVisible,
            enter = enterAnim,
            exit = exitAnim
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(height = 100.dp, width = 100.dp)
                        .background(Color.Blue)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    Modifier
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = tween(durationMillis = 5500)
                            ),
                            exit = slideOutVertically(
                                animationSpec = tween(durationMillis = 5500)
                            )
                        )
                        .size(width = 150.dp, height = 150.dp)
                        .background(
                            Color.Red
                        )
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimateVisibilityTheme {
        MainScreen()
    }
}