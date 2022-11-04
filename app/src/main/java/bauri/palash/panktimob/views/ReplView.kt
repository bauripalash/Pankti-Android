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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
fun runButton() {

    val btn_bg = SolidColor(Color(R.color.white))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Button(
            onClick = { /*TODO*/ },
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
fun codeInput() {
    val textval = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val scrollVal = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
            .verticalScroll(scrollVal),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textval.value,
            onValueChange = { textval.value = it },
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


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun codeOutput() {
    val outval = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = outval.value, onValueChange = { outval.value = it },
            placeholder = { Text(text = stringResource(id = R.string.code_output_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .height(200.dp)
        )

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topMenu(scope: CoroutineScope, dState: DrawerState) {
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        topMenu(scope, dState)
        Spacer(modifier = Modifier.size(5.dp))
        codeInput()
        Spacer(modifier = Modifier.size(5.dp))

        runButton()
        Spacer(modifier = Modifier.size(5.dp))
        codeOutput()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DefaultPreview() {
    PanktiMobTheme {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            /*topMenu()*/
            Spacer(modifier = Modifier.size(5.dp))
            codeInput()
            Spacer(modifier = Modifier.size(5.dp))

            runButton()
            Spacer(modifier = Modifier.size(5.dp))
            codeOutput()


        }
    }
}