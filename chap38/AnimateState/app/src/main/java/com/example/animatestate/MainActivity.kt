package com.example.animatestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animatestate.ui.theme.AnimateStateTheme

enum class BoxPosition {
    Start, End
}

enum class BoxColor {
    Red, Magenta
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    TransitionDemo()
                }
            }
        }
    }
}

@Composable
fun TransitionDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }

    /*
        val animatedOffset : Dp by animateDpAsState(
            targetValue = when (boxState) {
                BoxPosition.Start -> 0.dp
                BoxPosition.End -> LocalConfiguration.current.screenWidthDp.dp - 70.dp
            },
            animationSpec = tween(4000)
        )

        val animatedColor : Color by animateColorAsState(
            targetValue = when (boxState) {
                BoxPosition.Start -> Color.Red
                BoxPosition.End -> Color.Magenta
            }, animationSpec = tween(4000)
        )*/

    val transition = updateTransition(
        targetState = boxState,
        label = "Color and Motion"
    )

    val animatedOffset : Dp by transition.animateDp(transitionSpec = { tween(4000) }, label = "") { state ->
        when (state) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> LocalConfiguration.current.screenWidthDp.dp - 70.dp
        }
    }

    val animatedColor : Color by transition.animateColor(
        transitionSpec = {
            tween(4000)
        }, label = ""
    ) { state ->
        when (state) {
            BoxPosition.Start -> Color.Red
            BoxPosition.End -> Color.Magenta
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(70.dp)
                .background(animatedColor)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Start Animation")
        }
    }
}

@Composable
fun MotionDemo() {
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSlideLength = 70.dp

    val animatedOffset : Dp by animateDpAsState(
        targetValue = when (boxState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSlideLength
        },
        animationSpec = keyframes {
            durationMillis = 1000
            100.dp.at(10).with(LinearEasing)
            110.dp.at(500).with(FastOutSlowInEasing)
            200.dp.at(700).with(LinearOutSlowInEasing)
        }

        // animationSpec = spring(dampingRatio = DampingRatioHighBouncy, stiffness = StiffnessVeryLow)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(boxSlideLength)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { boxState = when(boxState){
                BoxPosition.Start -> BoxPosition.End
                BoxPosition.End -> BoxPosition.Start
            } },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}


@Composable
fun ColorChangeDemo() {
    var colorState by remember { mutableStateOf(BoxColor.Red) }

    val animatedColor : Color by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Magenta
            BoxColor.Magenta -> Color.Red
        }, animationSpec = tween(4500)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(200.dp)
                .background(animatedColor)
        )

        Button(
            onClick = { colorState = when(colorState){
                BoxColor.Red -> BoxColor.Magenta
                BoxColor.Magenta -> BoxColor.Red
            } },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Change Color")
        }
    }
}

@Composable
fun RotationDemo() {
    var rotated by remember { mutableStateOf(false) }

    val angle by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(durationMillis = 2500, easing = LinearEasing)
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.propeller),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(angle)
                .padding(10.dp)
                .size(300.dp)
        )

        Button(
            onClick = { rotated = !rotated },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Rotate Propeller")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimateStateTheme {
        TransitionDemo()
    }
}