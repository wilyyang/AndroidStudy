package com.example.slotapidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.slotapidemo.ui.theme.SlotApiDemoTheme
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlotApiDemoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var imageSelected by remember { mutableStateOf(true) }
    var linearSelected by remember { mutableStateOf(true) }

    val onTitleClick = { value : Boolean ->
        imageSelected = value
    }

    val onLinearClick = { value : Boolean ->
        linearSelected = value
    }

    ScreenContent(
        imageSelected = imageSelected,
        linearSelected = linearSelected,
        onTitleClick = onTitleClick,
        onLinearClick = onLinearClick,
        titleContent = {
            if (imageSelected) {
                TitleImage(drawing = R.drawable.ic_baseline_cloud_download_24)
            } else {
                Text("Downloading",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(30.dp)
                )
            }
        },
        progressContent = {
            if (linearSelected) {
                LinearProgressIndicator(Modifier.height(40.dp))
            } else {
                CircularProgressIndicator(Modifier.size(200.dp), strokeWidth = 18.dp)
            }
        }
    )
}

@Composable
fun ScreenContent(
    imageSelected: Boolean,
    linearSelected: Boolean,
    onTitleClick: (Boolean)->Unit,
    onLinearClick: (Boolean)->Unit,
    titleContent: @Composable () -> Unit,
    progressContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        titleContent()
        progressContent()
        CheckBoxes(
            imageSelected = imageSelected,
            linearSelected = linearSelected,
            onTitleClick = onTitleClick,
            onLinearClick = onLinearClick
        )
    }
}

@Composable
fun TitleImage(drawing: Int){
    Image(
        painter = painterResource(id = drawing),
        contentDescription = "title image"
    )
}

@Composable
fun CheckBoxes(
    imageSelected: Boolean,
    linearSelected: Boolean,
    onTitleClick: (Boolean)->Unit,
    onLinearClick: (Boolean)->Unit
){
    Row (
        Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = imageSelected,
            onCheckedChange = onTitleClick
        )
        Text("Image Title")

        Spacer(Modifier.width(20.dp))

        Checkbox(
            checked = linearSelected,
            onCheckedChange = onLinearClick
        )
        Text("Linear Progress")
    }
}

@Preview(showBackground = true)
@Composable
fun DemoPreview() {
    SlotApiDemoTheme {
        MainScreen()
    }
}