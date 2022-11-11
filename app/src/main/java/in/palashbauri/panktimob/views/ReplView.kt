package `in`.palashbauri.panktimob.views


import `in`.palashbauri.panktijapi.androidapi.Androidapi.doParse
import `in`.palashbauri.panktimob.R
import `in`.palashbauri.panktimob.readFromCache
import `in`.palashbauri.panktimob.saveToCache
import `in`.palashbauri.panktimob.ui.theme.NotoBengali
import `in`.palashbauri.panktimob.ui.theme.PanktiMobTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val CACHE_FILE_NAME = "replcache.txt"

@Composable
fun RunButton(clicked: () -> Unit) {


    val btnBg = SolidColor(Color(R.color.white))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        OutlinedButton(
            onClick = { clicked() },
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = btnBg),
            shape = RoundedCornerShape(10.dp)

        ) {
            Icon(painter = painterResource(id = R.drawable.ic_run), contentDescription = stringResource(
                id = R.string.run_button
            ))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(id = R.string.run_button))
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
fun CodeInput( inputValue : String , onChanged: (text: String) -> Unit) {


    var isNew by remember {
        mutableStateOf(true)
    }

    /*
    if (isNew) {
        inputValue = readFromCache(LocalContext.current, CACHE_FILE_NAME)
        onChanged(inputValue.text)
        isNew = false
    }

     */

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
            onValueChange = onChanged,
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
fun CodeOutput(resultValue: TextFieldValue) {
    val sState = rememberScrollState(0) //Scroll State for Output
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


    val thisContext = LocalContext.current
    var inputValue by rememberSaveable {
        mutableStateOf("")
    }


    var resultValue by remember {
        mutableStateOf(TextFieldValue(""))
    }




    val clickedRun = {
        //println("Button Clicked!")

        //checkAndGetExternal(thisContext , Manifest.permission.CAMERA , launcher)
        //launcher.launch(Manifest.permission.CAMERA)
        saveToCache(thisContext, CACHE_FILE_NAME, inputValue)
        val pd = doParse(inputValue)
        resultValue = TextFieldValue(pd)

        println(readFromCache(thisContext, CACHE_FILE_NAME))


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
            CodeInput(inputValue , onChanged = { inputValue = it })

            Spacer(modifier = Modifier.size(5.dp))

            RunButton(clickedRun)
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

        Repl(tempScope, tempDState)
    }
}