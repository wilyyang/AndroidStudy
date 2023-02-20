package com.example.boxlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boxlayout.ui.theme.BoxLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun TextCell(text:String, modifier:Modifier = Modifier, fontSize:Int = 150){
    val cellModifier = Modifier
        .padding(4.dp)
        .border(width = 5.dp, color = Color.Black)

    Surface {
        Text(
            text = text, cellModifier.then(modifier),
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun MainScreen() {
    Column {
        Box {
            Box(Modifier.size(100.dp).padding(10.dp).clip(RoundedCornerShape(30.dp)).background(Color.Blue))
            Box(Modifier.size(100.dp).padding(10.dp).clip(CircleShape).background(Color.Red))
            Box(Modifier.size(100.dp).padding(10.dp).clip(CutCornerShape(30.dp)).background(Color.Yellow))
        }
        Box(contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.size(400.dp, 400.dp)
        )   {
            val height = 200.dp
            val width = 200.dp

            TextCell("1", Modifier.size(width = width, height = height))
            TextCell("2", Modifier.size(width = width, height = height))
            TextCell("3", Modifier.size(width = width, height = height))

        }

        Box(modifier = Modifier.size(height = 90.dp, width = 290.dp)
        )   {
            Text("TopStart", Modifier.align(Alignment.TopStart))
            Text("TopCenter", Modifier.align(Alignment.TopCenter))
            Text("TopEnd", Modifier.align(Alignment.TopEnd))

            Text("CenterStart", Modifier.align(Alignment.CenterStart))
            Text("Center", Modifier.align(Alignment.Center))
            Text("CenterEnd", Modifier.align(Alignment.CenterEnd))

            Text("BottomStart", Modifier.align(Alignment.BottomStart))
            Text("BottomCenter", Modifier.align(Alignment.BottomCenter))
            Text("BottomEnd", Modifier.align(Alignment.BottomEnd))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BoxLayoutTheme {
        MainScreen()
    }
}