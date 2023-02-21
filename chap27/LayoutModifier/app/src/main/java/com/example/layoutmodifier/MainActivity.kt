package com.example.layoutmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutmodifier.ui.theme.LayoutModifierTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutModifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    LayoutScreen()
                }
            }
        }
    }
}

fun Modifier.exampleLayout(
    x:Int,
    y:Int
) = layout {  measurable, constraints ->

    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height){
        placeable.placeRelative(x = x, y = y)
    }
}

fun Modifier.exampleLayout2(fraction : Float) = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints)
    val x = -(placeable.width * fraction).roundToInt()

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x = x, y = 0)
    }
}

fun Modifier.exampleLayout3() = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints)

    val firstBaseLine = if(placeable[FirstBaseline] == AlignmentLine.Unspecified) 0 else placeable[FirstBaseline]
    val lastBaseLine = if(placeable[LastBaseline] == AlignmentLine.Unspecified) 0 else placeable[LastBaseline]

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x = firstBaseLine, y = lastBaseLine)
    }
}

@Composable
fun ColorBox(modifier : Modifier){
    Box(
        Modifier
            .padding(1.dp)
            .size(width = 50.dp, height = 10.dp)
            .then(modifier))
}

@Composable
fun MainScreen() {

    Column {
        Box(modifier = Modifier.size(120.dp, 80.dp)) {
            ColorBox(
                Modifier
                    .exampleLayout(90, 50)
                    .background(Color.Blue)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp, 80.dp)
        ) {
            Column {
                ColorBox(
                    Modifier
                        .exampleLayout2(0f)
                        .background(Color.Blue)
                )
                ColorBox(
                    Modifier
                        .exampleLayout2(0.25f)
                        .background(Color.Green)
                )
                ColorBox(
                    Modifier
                        .exampleLayout2(0.5f)
                        .background(Color.Yellow)
                )
                ColorBox(
                    Modifier
                        .exampleLayout2(0.25f)
                        .background(Color.Red)
                )
                ColorBox(
                    Modifier
                        .exampleLayout2(0.0f)
                        .background(Color.Magenta)
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutModifierTheme {
        LayoutScreen()
    }
}