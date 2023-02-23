package com.example.listdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.listdemo.ui.theme.ListDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Chap32Activity : ComponentActivity() {
    val channel = Channel<Int>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting2("Android")
                }
            }
        }
    }


    @Composable
    fun Greeting2(name : String) {
        val count = remember { mutableStateOf(1) }
        val coroutineScope = rememberCoroutineScope()


        LaunchedEffect(key1 = count.value){
            coroutineScope.launch(Dispatchers.Main) {
                performTask1()
            }

            coroutineScope.launch(Dispatchers.Main) {
                performTask2()
            }
        }

        Button(onClick = {
            count.value += 1
            coroutineScope.launch(Dispatchers.Main) {

                performSlowTask()
            }
        }){
            Text(text = "Click Me")
        }
    }

    suspend fun performTask1(){
        (1..6).forEach{
            channel.send(it)
        }
    }

    suspend fun performTask2(){
        repeat(6){
            println("perform Received: ${channel.receive()}")
        }
    }

    suspend fun performSlowTask() {
        println("performSlowTask before")
        delay(5000)
        println("performSlowTask after")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        ListDemoTheme {
            Greeting2("Android")
        }
    }
}