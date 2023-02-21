package com.example.layoutmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutmodifier.ui.theme.LayoutModifierTheme

class LayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutModifierTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    LayoutScreen()
                }
            }
        }
    }
}

@Composable
fun DoNothingLayout(
    modifier : Modifier = Modifier,
    content : @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map {  measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = 0)
            }

        }
    }
}

@Composable
fun LayoutScreen() {
    DoNothingLayout(Modifier.padding(8.dp)) {
        Text("Text Line 1")
        Text("Text Line 2")
        Text("Text Line 3")
        Text("Text Line 4")
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
    LayoutModifierTheme {
        LayoutScreen()
    }
}