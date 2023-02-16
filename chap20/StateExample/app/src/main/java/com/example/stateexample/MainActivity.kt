package com.example.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.stateexample.ui.theme.StateExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DemoScreen()
                }
            }
        }
    }
}

@Composable
fun DemoScreen() {
    // var textState = { mutableStateOf("") }
    // var textState = remember { mutableStateOf("") }
    var textState by rememberSaveable { mutableStateOf("") }
    // var (textValue, setText) = remember { mutableStateOf("") }

    val onTextChange = { text : String ->
        textState = text
    }

    var switchState by remember {
        mutableStateOf(true)
    }

    val onSwitchChange = {  value:Boolean ->
        switchState = value
    }


    Column {
        MyTextField(textState, onTextChange)
        FunctionB(switchState = switchState, onSwitchChange = onSwitchChange)
    }

}

@Composable
fun MyTextField(text: String, onTextChange: (String) -> Unit){


    TextField(
        value = text,
        onValueChange = onTextChange
    )
}

@Composable
fun FunctionB(switchState: Boolean, onSwitchChange: (Boolean) -> Unit){
    Switch(
        checked = switchState,
        onCheckedChange = onSwitchChange
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StateExampleTheme {
        DemoScreen()

    }
}