package bauri.palash.panktimob.views

import android.widget.Scroller
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bauri.palash.panktimob.R
import bauri.palash.panktimob.ui.theme.PanktiMobTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidapi.Androidapi.doParse
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import bauri.palash.panktimob.ui.theme.NotoBengali


@Composable
fun runButton(clicked : () -> Unit) {

    val btn_bg = SolidColor(Color(R.color.white))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Button(
            onClick = { clicked() },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = btn_bg),
            shape = RoundedCornerShape(10.dp)

        ) {
            Text(text = "Run />")
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu(scope: CoroutineScope, dState: DrawerState) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                if (dState.isClosed) {
                    scope.launch { dState.open() }
                } else if (dState.isOpen) {
                    scope.launch { dState.close() }
                }

            },
        ) {
            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
        }

        Text(text = "Pankti", modifier = Modifier.weight(1f))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeInput( onChanged : (text : String) -> Unit ){
    var inputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var lState = rememberLazyListState()

    var sState = rememberScrollState(0) //Scroll State for TextField
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.heightIn(550.dp , 550.dp)
            .height(550.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            textStyle = TextStyle(fontFamily = NotoBengali),
            value = inputValue,
            onValueChange = {
                                inputValue = it

                                onChanged(inputValue.text)

                            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.code_input_hint)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .height(550.dp)
                .heightIn(550.dp, 550.dp)


                //.verticalScroll(sState , enabled = true)
                //.scrollable(state = sState, orientation = Orientation.Vertical)
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeOutput( resultValue : TextFieldValue ){
    var sState = rememberScrollState(0) //Scroll State for Output
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = resultValue, onValueChange = { /* No Need of anything here, I guess*/ },
            placeholder = { Text(text = stringResource(id = R.string.code_output_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .height(350.dp)
                .verticalScroll(sState, enabled = true)
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Repl(scope: CoroutineScope, dState: DrawerState) {
    var inputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var resultValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val InputChanged : (value : String) -> Unit = {
        inputValue = TextFieldValue(it)
    }


    val clickedRun = {
        println("Button Clicked!")
        val pd = doParse(inputValue.text)
        resultValue = TextFieldValue(pd)
    }



    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TopMenu(scope, dState)
            Spacer(modifier = Modifier.size(5.dp))

            // Code Input
            CodeInput(onChanged = InputChanged)

            Spacer(modifier = Modifier.size(5.dp))

            runButton(clickedRun)
            Spacer(modifier = Modifier.size(5.dp))

            //Code Output / Result
            CodeOutput(resultValue = resultValue)
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PanktiMobTheme {
        val tempScope = rememberCoroutineScope()
        val tempDState = rememberDrawerState(initialValue = DrawerValue.Closed)

        Repl(tempScope , tempDState)
    }
}