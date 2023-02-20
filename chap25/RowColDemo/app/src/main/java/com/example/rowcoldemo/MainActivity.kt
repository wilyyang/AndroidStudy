package com.example.rowcoldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rowcoldemo.ui.theme.RowColDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RowColDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier){
    val cellModifier = Modifier
        .padding(4.dp)
        .size(100.dp, 100.dp)
        .border(width = 4.dp, color = Color.Black)



    Text(
        text = text,
        cellModifier.then(modifier),
        fontSize = 70.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MainScreen() {
    val layoutModifier = Modifier
        .border(width = 1.dp, color = Color.Gray)

    Column {
        Row {
            Column(
                modifier = Modifier
                    .size(width = 250.dp, height = 400.dp)
                    .then(layoutModifier),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                TextCell("1")
                TextCell("2")
                TextCell("3")
            }

            Column (
                modifier = layoutModifier
                ){
                TextCell("4")
                TextCell("5")
                TextCell("6")
            }
        }

        Row(
            modifier = Modifier
                .size(width = 400.dp, height = 250.dp)
                .then(layoutModifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround

        ){
            TextCell("9", Modifier.align(Alignment.Top))
            TextCell("10", Modifier.align(Alignment.CenterVertically))
            TextCell("11", Modifier.align(Alignment.Bottom))
        }

        Row{
            Text(text = "Large Text\nMore Text", Modifier.alignBy(FirstBaseline), fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Small Text", modifier = Modifier.paddingFrom(alignmentLine = FirstBaseline, before = 80.dp, after = 0.dp),
                fontSize = 32.sp, fontWeight = FontWeight.Bold
            )
        }

        Row{
            TextCell("1", Modifier.weight(weight = 0.2f, fill = true))
            TextCell("2", Modifier.weight(weight = 0.4f, fill = true))
            Text(text = "2.5", Modifier.width(20.dp))
            TextCell("3", Modifier.weight(weight = 0.3f, fill = true))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RowColDemoTheme {
        MainScreen()
    }
}