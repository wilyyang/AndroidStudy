package com.example.customlayout

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.customlayout.ui.theme.CustomLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun CascadeLayout(
    modifier : Modifier = Modifier,
    spacing : Int = 0,
    content : @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measureables, constraints ->

        val placeables = measureables.map { measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var indent = 0
            var yCoord = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = indent, y = yCoord)
                indent += placeable.width  + spacing
                yCoord += placeable.height + spacing
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box {
       CascadeLayout(spacing = 30) {
           Box(modifier = Modifier.size(60.dp).background(Color.Blue))
           Box(modifier = Modifier.size(80.dp, 40.dp).background(Color.Red))
           Box(modifier = Modifier.size(90.dp, 100.dp).background(Color.Cyan))
           Box(modifier = Modifier.size(50.dp).background(Color.Magenta))
           Box(modifier = Modifier.size(70.dp).background(Color.Green))
       }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CustomLayoutTheme {
        MainScreen()
    }
}