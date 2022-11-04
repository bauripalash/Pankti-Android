package bauri.palash.panktimob

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.*


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview

import bauri.palash.panktimob.ui.theme.PanktiMobTheme
import bauri.palash.panktimob.views.Drawer
import bauri.palash.panktimob.views.Repl


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val dScope = rememberCoroutineScope()
    val dState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //Repl()
    Drawer(scope = dScope, dState = dState)
}


@Preview
@Composable
fun DefaultPreview() {
    PanktiMobTheme {


      MainView()
    }
}