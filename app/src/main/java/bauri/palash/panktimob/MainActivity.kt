package bauri.palash.panktimob

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bauri.palash.panktimob.ui.theme.PanktiMobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PanktiMobTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!\n")

}
@Composable
fun RunButton(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.padding(all= Dp(10F)),
            enabled = true,
            border = BorderStroke(width = 1.dp , brush = SolidColor(Color.Blue))

        ) {
            Text(text = "Run />")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeInput(){
    val textval = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val scrollVal = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .verticalScroll(scrollVal),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = textval.value, onValueChange = { textval.value = it } , placeholder = {Text(
            text = stringResource(id = R.string.code_input_hint)
        )} , modifier = Modifier.fillMaxWidth().fillMaxHeight().height(300.dp))




    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeOutput(){
    val outval = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = outval.value, onValueChange = { outval.value = it },
        placeholder = { Text(text = stringResource(id = R.string.code_output_hint)) },
            modifier = Modifier.fillMaxWidth().fillMaxHeight().height(200.dp)
            )

    }


}
@Composable
fun MainView(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        CodeInput()
        Spacer(modifier = Modifier.size(16.dp))

        RunButton()
        Spacer(modifier = Modifier.size(16.dp))
        CodeOutput()
    }

}

@Preview
@Composable
fun DefaultPreview() {
    PanktiMobTheme {


      MainView()

    }
}