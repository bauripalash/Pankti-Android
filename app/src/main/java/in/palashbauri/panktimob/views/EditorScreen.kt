package `in`.palashbauri.panktimob.views

import `in`.palashbauri.panktijapi.androidapi.Androidapi.doParse
import `in`.palashbauri.panktimob.R
import `in`.palashbauri.panktimob.readFromCache
import `in`.palashbauri.panktimob.saveToCache
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader

const val EDITOR_CACHE = "editorcache.txt"

sealed class EditorScreenItems(var title: String, var route: String) {
    object Write : EditorScreenItems("Write", "write")
    object RunResult : EditorScreenItems("Result", route = "result")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeEdit(onChanged: (text: String) -> Unit, iv: MutableState<TextFieldValue>) {

    var isNew by remember {
        mutableStateOf(true)
    }

    if (isNew) {
        iv.value = TextFieldValue(readFromCache(LocalContext.current, EDITOR_CACHE))
        onChanged(iv.value.text)
        isNew = false
    }
    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = iv.value, onValueChange = {
            iv.value = it
            onChanged(iv.value.text)
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

private fun showToastError(appCon: Context, msg: String) {
    Toast.makeText(appCon, msg, Toast.LENGTH_LONG).show()
}

fun createFile(appCon: Context, uri: String, data: String) {
    Log.d("createFILE", uri)
    val filePath = Uri.parse(uri)
    //val fileObject = filePath.path?.let { File(it) }
    try {

        val parcelFileDescriptor = appCon.contentResolver.openFileDescriptor(filePath, "rw")

        val fileOutputStream = FileOutputStream(parcelFileDescriptor?.fileDescriptor)

        fileOutputStream.write(data.toByteArray())
        fileOutputStream.close()
        parcelFileDescriptor?.close()
    } catch (e: java.lang.Exception) {
        //e.printStackTrace()
        showToastError(appCon, "Failed to Save file $uri")
    }

}

private fun openFile(appCon: Context, uri: String): String? {
    val filePath = Uri.parse(uri)

    try {
        val parcelFileDescriptor = appCon.contentResolver.openFileDescriptor(filePath, "r")
        val fileInputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val output = FileReader(parcelFileDescriptor?.fileDescriptor).readText()

        //Log.w("OUTPUT : " , output)
        fileInputStream.close()
        parcelFileDescriptor?.close()
        return output
    } catch (e: Exception) {
        showToastError(appCon, "Failed to Open File $uri")
    }

    return null
}

@SuppressLint("UnrememberedMutableState")
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

    val aLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/*"),
        onResult = {
            createFile(thisContext, it.toString(), inputValue.text)
        })
    val runClicked = {
        //SaveToTempEditorFile(thisContext , inputValue.text)
        saveToCache(thisContext, EDITOR_CACHE, inputValue.text)
        val pd = doParse(inputValue.text)

        resultValue = TextFieldValue(pd)

    }

    val saveClicked = {
        try {
            aLauncher.launch("source.pank")
        } catch (e: Exception) {
            showToastError(thisContext, "Failed to save File")
        }
    }

    val oLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
            //println(it.toString())
            val inpFileValue = openFile(thisContext, it.toString())
            Log.w("Launcher", inpFileValue.toString())
            if (inpFileValue != null) {
                inputValue = TextFieldValue(inpFileValue.toString())
            }

        }

    val openClicked = {
        oLauncher.launch(arrayOf("*/*"))

    }



    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {

            RunButton(clicked = runClicked, modifier = Modifier.weight(0.4F))
            SaveButton(clicked = saveClicked, modifier = Modifier.weight(0.4F))
            OpenButton(clicked = openClicked, modifier = Modifier.weight(0.4F))

        }
        NavHost(navController, startDestination = EditorScreenItems.Write.route) {
            composable(EditorScreenItems.Write.route) {

                CodeEdit(inputChanged, mutableStateOf(inputValue)) //Not good!
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