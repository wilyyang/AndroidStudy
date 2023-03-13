package com.example.flowdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
fun ScreenSetup(viewModel : DemoViewModel = viewModel()) {
    // MainScreen(viewModel.newFlow)
    MainScreen6(viewModel)
}

@Composable
fun MainScreen6(viewModel : DemoViewModel) {
    val count by viewModel.sharedFlow.collectAsState(initial = 0)
    val count2 by viewModel.sharedFlow.collectAsState(initial = 0)
    val count3 by viewModel.sharedFlow.collectAsState(initial = 0)
    val num by viewModel.subCount.collectAsState(initial = 0)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count $num", style = TextStyle(fontSize = 40.sp))
        Button(onClick = { viewModel.startSharedFlow()}){
            Text("Click Me")
        }
    }
}



@Composable
fun MainScreen5(viewModel : DemoViewModel) {
    val count by viewModel.stateFlow.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
        Button(onClick = { viewModel.increaseValue()}){
            Text("Click Me")
        }
    }
}

@Composable
fun MainScreen4(viewModel : DemoViewModel) {
    var count by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        val flow1 = (1 .. 5).asFlow().onEach { delay(1000) }
        val flow2 = flowOf("one", "two", "three", "four").onEach { delay(1500) }
        flow1.combine(flow2) { value, string -> "$value, $string" }
            .collect { count = it }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    }
}

@OptIn(FlowPreview::class)
@Composable
fun MainScreen3(viewModel : DemoViewModel) {
    var count by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        viewModel.myFlow.flatMapMerge { viewModel.doubleIt(it) }
            .collect { count = it }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    }
}

@Composable
fun MainScreen2(flow : Flow<Int>) {
    var count by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = Unit) {
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

    var count by remember { mutableStateOf("Current value=") }
    LaunchedEffect(key1 = Unit) {
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