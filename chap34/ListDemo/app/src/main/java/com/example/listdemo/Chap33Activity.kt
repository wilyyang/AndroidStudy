package com.example.listdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listdemo.ui.theme.ListDemoTheme
import kotlinx.coroutines.launch

class Chap33Activity : ComponentActivity() {

    private lateinit var cars: Array<String>
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        cars = resources.getStringArray(R.array.car_array)
        setContent {
            ListDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen4("Android")
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MainScreen4(name : String) {

        Column {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 60.dp),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(30) { index ->
                    Card(
                        backgroundColor = Color.Blue, modifier = Modifier
                            .padding(5.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            "$index",
                            fontSize = 35.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Divider()

            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(15) { index ->
                    Card(
                        backgroundColor = Color.Blue, modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        elevation = 3.dp
                    ) {
                        Text(
                            "$index",
                            fontSize = 35.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    }

    @Composable
    fun MyListItem(text:String = "") {
        Button(modifier = Modifier.padding(5.dp), onClick = {}){
            Text(text = text)
        }
    }



    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MainScreen3(name : String) {
        val colorNameList = listOf("Red", "Green", "Blue", "Indigo", "PAPA", "SMUPH", "WILY", "LOL")
        val coroutineScope = rememberCoroutineScope()

        Column {
            val scrollState = rememberScrollState()
            Column (modifier = Modifier
                .height(200.dp)
                .verticalScroll(scrollState)){
                repeat(10){
                    MyListItem()
                }
            }
            Divider()

            val listState = rememberLazyListState()
            val firstVisible = listState.firstVisibleItemIndex
            LazyColumn(modifier = Modifier.height(200.dp), state = listState){
                items(10){ index ->
                    MyListItem("This is item $index")
                }
            }

            Log.e("Crane", "$firstVisible")
            if(firstVisible >= 6){
                Button(onClick = { coroutineScope.launch{
                    listState.animateScrollToItem(0)
                } }) {
                    Text("처음으로 돌아가기")
                }
            }

            Divider()

            LazyColumn(modifier = Modifier.height(200.dp)){
                itemsIndexed(colorNameList) { index, item ->
                    MyListItem("$index = $item")
                }
            }
            Divider()

            Button(onClick = {
                coroutineScope.launch{
                    scrollState.animateScrollTo(scrollState.maxValue)
                    listState.animateScrollToItem(colorNameList.lastIndex)
                }
            }){

            }
            Divider()

            val groupedCars = cars.groupBy {it.substringBefore(' ')}

            LazyColumn(modifier = Modifier.height(200.dp)){
                groupedCars.forEach {  (brand, models)->
                    stickyHeader {
                        Text(
                            text = brand,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray)
                                .padding(5.dp)
                                .fillMaxWidth()
                        )
                    }

                    items(models){ model ->
                        MyListItem(model)
                    }
                }
            }
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview3() {
        ListDemoTheme {
            MainScreen4("Android")
        }
    }
}