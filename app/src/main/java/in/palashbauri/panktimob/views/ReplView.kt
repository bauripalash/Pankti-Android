package `in`.palashbauri.panktimob.views


import `in`.palashbauri.panktijapi.androidapi.Androidapi.doParse
import `in`.palashbauri.panktimob.R
import `in`.palashbauri.panktimob.readFromCache
import `in`.palashbauri.panktimob.saveToCache
import `in`.palashbauri.panktimob.ui.theme.NotoBengali
import `in`.palashbauri.panktimob.ui.theme.PanktiMobTheme
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

const val CACHE_FILE_NAME = "repl-cache.txt"


@Composable
fun RunButton(appCon: Context, clicked: () -> Unit) {


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
            Icon(
                painter = painterResource(id = R.drawable.ic_run),
                contentDescription = appCon.getString(R.string.run_button)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(appCon.getString(R.string.run_button))
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeInput(appCon: Context, inputValue: String, onChanged: (text: String) -> Unit) {


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
                    text = appCon.getString(R.string.code_input_hint)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .height(550.dp)
                .heightIn(550.dp, 550.dp)

        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeOutput(appCon: Context, resultValue: TextFieldValue) {
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
            placeholder = { Text(text = appCon.getString(R.string.code_output_hint)) },
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
fun Repl(scope: CoroutineScope, dState: DrawerState, navController: NavController) {


    val thisContext = LocalContext.current
    var inputValue by rememberSaveable {
        mutableStateOf("")
    }


    var resultValue by remember {
        mutableStateOf(TextFieldValue(""))
    }


    val clickedRun = {
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
            TopMenu(scope, dState, navController)
            Spacer(modifier = Modifier.size(5.dp))

            // Code Input
            CodeInput(thisContext, inputValue, onChanged = { inputValue = it })

            Spacer(modifier = Modifier.size(5.dp))

            RunButton(thisContext, clickedRun)
            Spacer(modifier = Modifier.size(5.dp))

            //Code Output / Result
            CodeOutput(thisContext, resultValue = resultValue)
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
        val navController = rememberNavController()

        Repl(tempScope, tempDState, navController)
    }
}