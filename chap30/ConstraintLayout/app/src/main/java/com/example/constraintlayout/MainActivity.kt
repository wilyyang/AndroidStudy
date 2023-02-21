package com.example.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.example.constraintlayout.ui.theme.ConstraintLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MyButton(text : String, modifier : Modifier = Modifier) {
    Button(onClick = {}, modifier = modifier) {
        Text(text)
    }
}

@Composable
fun MainScreen() {
    ConstraintLayout {
        val (layout1, layout2, layout3, layout4) = createRefs()

        ConstraintLayout(
            Modifier
                .constrainAs(layout1) {
                    centerHorizontallyTo(parent)
                    linkTo(top = parent.top, bottom = layout2.top)

                }) {
            val (button1, button2, button3) = createRefs()

            MyButton(text = "Button1", Modifier.constrainAs(button1) {
                // top.linkTo(parent.top, margin = 60.dp)
                // linkTo(start = parent.start, startMargin = 30.dp, end = parent.end)
                // centerTo(parent)

                linkTo(parent.start, parent.end, bias = 0.8f, startMargin = 5.dp, endMargin = 50.dp)
                top.linkTo(parent.top)
                bottom.linkTo(button2.top)
            })

            MyButton(text = "Button2", Modifier.constrainAs(button2) {
                centerHorizontallyTo(parent)
                top.linkTo(button1.bottom)
                bottom.linkTo(parent.bottom)
            })

            Text(text = "텍스트", Modifier.constrainAs(button3) {
                centerAround(button1.start)
                centerAround(button2.top)
            })
        }

        ConstraintLayout(
            Modifier
                .width(400.dp)
                .constrainAs(layout2) {
                    centerHorizontallyTo(parent)
                    linkTo(top = layout1.bottom, bottom = layout3.top)
                }) {

            val (button4, button5, button6) = createRefs()
            createHorizontalChain(button4, button5, button6, chainStyle = ChainStyle.SpreadInside)

            MyButton(text = "Button4", Modifier.constrainAs(button4) {
                centerVerticallyTo(parent)
            })

            MyButton(text = "Button5", Modifier.constrainAs(button5) {
                centerVerticallyTo(parent)
            })

            MyButton(text = "Button6", Modifier.constrainAs(button6) {
                centerVerticallyTo(parent)
            })
        }

        ConstraintLayout(
            Modifier.constrainAs(layout3) {
                    centerHorizontallyTo(parent)
                    linkTo(top = layout2.bottom, bottom = layout4.top)
                }) {

            val (button7, button8, button9) = createRefs()
            val guide = createGuidelineFromStart(fraction = .60f)

            MyButton(text = "Button7", Modifier.constrainAs(button7) {
                top.linkTo(parent.top, margin = 30.dp)
                end.linkTo(guide, margin = 30.dp)
            })

            MyButton(text = "Button8", Modifier.constrainAs(button8) {
                top.linkTo(button7.bottom, margin = 20.dp)
                start.linkTo(guide, margin = 40.dp)
            })

            MyButton(text = "Button9", Modifier.constrainAs(button9) {
                top.linkTo(button8.bottom, margin = 40.dp)
                end.linkTo(guide, margin = 20.dp)
            })
        }

        ConstraintLayout(
            Modifier
                .size(width = 350.dp, height = 180.dp)
                .constrainAs(layout4) {
                    centerHorizontallyTo(parent)
                    linkTo(top = layout3.bottom, bottom = parent.bottom)
                }) {

            val (button1, button2, button3) = createRefs()
            val barrier = createEndBarrier(button1, button2)

            MyButton(text = "Button1",
                Modifier
                    .width(100.dp)
                    .constrainAs(button1) {
                        top.linkTo(parent.top, margin = 30.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                    })

            MyButton(text = "Button2",
                Modifier
                    .width(100.dp)
                    .constrainAs(button2) {
                        top.linkTo(button1.bottom, margin = 20.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                    })

            MyButton(text = "Button3", Modifier.constrainAs(button3) {
                linkTo(parent.top, parent.bottom, topMargin = 8.dp, bottomMargin = 8.dp)
                start.linkTo(barrier, margin = 30.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })
        }
    }
}

private fun myConstraintSet(margin : Dp) : ConstraintSet {
    return ConstraintSet {
        val button1 = createRefFor("testId")
        constrain(button1) {
            linkTo(
                parent.top, parent.bottom,
                topMargin = margin, bottomMargin = margin
            )

            linkTo(
                parent.start, parent.end,
                startMargin = margin, endMargin = margin
            )

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    }
}

@Composable
fun MainScreen2(){
    val constraints = myConstraintSet(margin = 40.dp)
    ConstraintLayout(
        constraints,
        Modifier.size(width = 200.dp, height = 200.dp)
    ) {

        MyButton(text = "Button1", Modifier.layoutId("testId"))
        Text("test")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ConstraintLayoutTheme {
        MainScreen2()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConstraintLayoutTheme {
        MainScreen()
    }
}