package com.example.flowdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.flowdemo.ui.theme.FlowDemoTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowDemoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
    // MainScreen(viewModel.newFlow)
    MainScreen2(viewModel.myFlow)
}

@Composable
fun MainScreen2(flow : Flow<Int>) {
    var count by remember { mutableStateOf(0)}
    LaunchedEffect(key1 = Unit){
        flow.fold(10) { accumulator, value ->
            count = accumulator + value
            Log.e("Crane", "Proc >> idx : value : $value / acc : $accumulator / count : $count result : ${accumulator + value}")
            accumulator + value
        }

    }
}

@Composable
fun MainScreen(flow : Flow<String>) {
    // val count by flow.collectAsState(initial = "Current value =")
    // LaunchedEffect(key1 = count){
    //     delay(2000)
    //     Log.e("Crane", "Proc >> idx : $count")
    // }

    var count by remember { mutableStateOf("Current value=")}
    LaunchedEffect(key1 = Unit){
        val elapsedTime = measureTimeMillis {
            flow.buffer().collect {
                count = it
                delay(1000)
                Log.e("Crane", "Proc >> idx : $count")
            }
        }
        count = "Duration = $elapsedTime"
        Log.e("Crane", count)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlowDemoTheme {
        ScreenSetup()
    }
}