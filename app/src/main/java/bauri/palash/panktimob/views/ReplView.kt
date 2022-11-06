package bauri.palash.panktimob.views

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
fun Repl(scope: CoroutineScope, dState: DrawerState) {
    var inputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var resultValue by remember {
        mutableStateOf(TextFieldValue(""))
    }



    //val inputValue = mutableStateOf(TextFieldValue(""))
    //resultValue = TextFieldValue

    val clickedRun = {
        println("Button Clicked!")
        resultValue = inputValue
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {inputValue = it},
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.code_input_hint)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .height(550.dp))


            }
            Spacer(modifier = Modifier.size(5.dp))

            runButton(clickedRun)
            Spacer(modifier = Modifier.size(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = resultValue, onValueChange = { resultValue = it },
                    placeholder = { Text(text = stringResource(id = R.string.code_output_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .height(200.dp)
                )

            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DefaultPreview() {
    PanktiMobTheme {

        //Repl(scope =, dState =  DrawerState())
    }
}