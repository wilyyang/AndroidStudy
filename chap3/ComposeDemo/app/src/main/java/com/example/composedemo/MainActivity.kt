package com.example.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    DemoScreen()
                }
            }
        }
    }

    @Composable
    fun DemoScreen(){
        var sliderPosition by remember { mutableStateOf(10f) }
        val handlePositionChange = { position : Float ->
            sliderPosition = position
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            DemoText(message = "Welcome to Compoase", fontSize = sliderPosition)

            Spacer(modifier = Modifier.height(150.dp))

            DemoSlider(
                sliderPosition = sliderPosition,
                onPositionChange = handlePositionChange
            )

            Text(
                style = MaterialTheme.typography.h2,
                text = sliderPosition.toInt().toString() + "sp"
            )
        }

    }

    @Composable
    fun DemoText(message: String, fontSize: Float) {
        Text(
            text = message,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun DemoSlider(sliderPosition : Float, onPositionChange : (Float) -> Unit) {
        Slider(
            modifier = Modifier.padding(10.dp),
            valueRange = 5f .. 25f,
            value = sliderPosition,
            onValueChange = onPositionChange
        )
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        ComposeDemoTheme {
            DemoScreen()
        }
    }

    // Chapter 22//

    @Composable
    fun SlotDemo2(
        topContent: @Composable () -> Unit,
        middleContent: @Composable () -> Unit,
        bottomContent: @Composable () -> Unit){
            Column {
                topContent()
                middleContent()
                bottomContent()
            }
    }

    @Composable
    fun SlotDemo(middleContent: @Composable () -> Unit){
        Column {
            Text("Top Text")
            middleContent()
            Text("Bottom Text")
        }
    }

    @Composable
    fun ButtonDemo(){
        Button(onClick = {}){
            Text("Click Me")
        }
    }

    @Preview
    @Composable
    fun SlotPreview(){
        Column {
            SlotDemo {
                ButtonDemo()
            }

            SlotDemo2 (
                { Text("Top Text") },
                { ButtonDemo() },
                { Text("Bottom Text") },
            )
        }
    }

}