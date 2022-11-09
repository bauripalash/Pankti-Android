package bauri.palash.panktimob.views

import `in`.palashbauri.panktijapi.androidapi.Androidapi.doParse
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bauri.palash.panktimob.R
import bauri.palash.panktimob.readFromCache
import bauri.palash.panktimob.saveToCache

const val EDITOR_CACHE = "editorcache.txt"

sealed class EditorScreenItems(var title: String, var route: String) {
    object Write : EditorScreenItems("Write", "write")
    object RunResult : EditorScreenItems("Result", route = "result")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeEdit(onChanged: (text: String) -> Unit) {


    var inputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var isNew by remember {
        mutableStateOf(true)
    }

    if (isNew) {
        inputValue = TextFieldValue(readFromCache(LocalContext.current, EDITOR_CACHE))
        onChanged(inputValue.text)
        isNew = false
    }
    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            onChanged(inputValue.text)
        }, placeholder = {
            Text(text = stringResource(id = R.string.code_input_hint))
        }, modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)


        )

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeResult(resultValue: TextFieldValue) {
    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = resultValue,
            onValueChange = { /* Not needed */ },
            placeholder = { Text(text = stringResource(id = R.string.code_output_hint)) },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)


        )

    }
}

@Composable
fun RunButton(clicked: () -> Unit, modifier: Modifier) {
    OutlinedButton(onClick = clicked, modifier = modifier) {
        Text(text = "Run/>")
    }
}

@Composable
fun SaveButton(clicked: () -> Unit, modifier: Modifier) {
    OutlinedButton(onClick = clicked, modifier = modifier) {
        Text(text = "Save")
    }
}

@Composable
fun OpenButton(clicked: () -> Unit, modifier: Modifier) {
    OutlinedButton(onClick = clicked, modifier = modifier) {
        Text(text = "Open")
    }
}


@Composable
fun EditorWriteScreen(navController: NavHostController) {
    var inputValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var resultValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val inputChanged: (value: String) -> Unit = {
        inputValue = TextFieldValue(it)
    }

    val thisContext = LocalContext.current
    val runClicked = {
        //SaveToTempEditorFile(thisContext , inputValue.text)
        saveToCache(thisContext, EDITOR_CACHE, inputValue.text)
        val pd = doParse(inputValue.text)

        resultValue = TextFieldValue(pd)
    }



    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {

            RunButton(clicked = runClicked, modifier = Modifier.weight(0.4F))
            SaveButton(clicked = runClicked, modifier = Modifier.weight(0.4F))
            OpenButton(clicked = runClicked, modifier = Modifier.weight(0.4F))

        }
        NavHost(navController, startDestination = EditorScreenItems.Write.route) {
            composable(EditorScreenItems.Write.route) {
                CodeEdit(inputChanged)
            }
            composable(EditorScreenItems.RunResult.route) {
                CodeResult(resultValue)
            }

        }
    }
}


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorView() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNav(navController) }) {
        EditorWriteScreen(navController = navController)
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    var sItem by remember {
        mutableStateOf(0)
    }

    NavigationBar {
        //items.forEachIndexed{index , item ->
        NavigationBarItem(selected = sItem == 0,
            onClick = {
                sItem = 0
                navController.navigate(EditorScreenItems.Write.route) {
                    popUpTo(navController.graph.startDestDisplayName)
                }

            },
            label = { Text(text = EditorScreenItems.Write.title) },
            icon = { Icon(Icons.Filled.Edit, contentDescription = EditorScreenItems.Write.title) })

        NavigationBarItem(selected = sItem == 1, onClick = {
            sItem = 1
            navController.navigate(EditorScreenItems.RunResult.route) {
                popUpTo(navController.graph.startDestDisplayName)
            }

        }, label = { Text(text = EditorScreenItems.RunResult.title) }, icon = {
            Icon(
                Icons.Filled.Build, contentDescription = EditorScreenItems.RunResult.title
            )
        })
        //}
    }
}