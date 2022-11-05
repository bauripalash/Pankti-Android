package bauri.palash.panktimob.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bauri.palash.panktimob.R

sealed class EditorScreenItems(var title : String , var route : String){
    object Write : EditorScreenItems("Write" , "write")
    object RunResult : EditorScreenItems("Result" , route = "result")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeEdit(){


        val outval = remember {
            mutableStateOf(TextFieldValue(""))
        }
        Column(
            modifier = Modifier.fillMaxSize(),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = outval.value, onValueChange = { outval.value = it },
                placeholder = { Text(text = stringResource(id = R.string.code_input_hint)) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp)


            )

        }



}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeResult(){
    val outval = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = outval.value, onValueChange = { outval.value = it },
            placeholder = { Text(text = stringResource(id = R.string.code_output_hint)) },
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)


        )

    }
}

@Composable
fun EditorWriteScreen(navController: NavHostController){
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { /*TODO*/ } , modifier = Modifier.weight(0.4F)) {
                Text(text = "Run/>")
            }

            OutlinedButton(onClick = { /*TODO*/ } , modifier = Modifier.weight(0.4F)) {
                Text(text = "Save")
            }
            OutlinedButton(onClick = { /*TODO*/ } , modifier = Modifier.weight(0.4F)) {
                Text(text = "Open")
            }
        }
        NavHost(navController, startDestination = EditorScreenItems.Write.route) {
            composable(EditorScreenItems.Write.route) {
                CodeEdit()
            }
            composable(EditorScreenItems.RunResult.route) {
                CodeResult()
            }

        }
    }
}

@Composable
fun EditorScreen( name : String ){
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedButton(onClick = { /*TODO*/ } , modifier = Modifier.weight(1F)) {
            Text(text = "Click Me")
        }

        //BottomNav()
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorView(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNav(navController) }
    ) {
        EditorWriteScreen(navController = navController)
    }
}

@Composable
fun BottomNav(navController: NavHostController){
    var sItem by remember {
         mutableStateOf(0)
    }

    val items = listOf(EditorScreenItems.Write , EditorScreenItems.RunResult)

    NavigationBar() {
        //items.forEachIndexed{index , item ->
            NavigationBarItem(selected = sItem == 0, onClick = {
                sItem = 0
                navController.navigate(EditorScreenItems.Write.route){
                    popUpTo(navController.graph.startDestDisplayName)
                }

            } , label = { Text(text = EditorScreenItems.Write.title) } ,
            icon = { Icon(Icons.Filled.Edit , contentDescription = EditorScreenItems.Write.title) }
                )

        NavigationBarItem(selected = sItem == 1, onClick = {
            sItem = 1
            navController.navigate(EditorScreenItems.RunResult.route){
                popUpTo(navController.graph.startDestDisplayName)
            }

        } , label = { Text(text = EditorScreenItems.RunResult.title) } ,
            icon = { Icon(Icons.Filled.Build , contentDescription = EditorScreenItems.RunResult.title) }
        )
        //}
    }
}