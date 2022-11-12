package `in`.palashbauri.panktimob


import `in`.palashbauri.panktimob.ui.theme.PanktiMobTheme
import `in`.palashbauri.panktimob.views.Drawer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("bn"))
        AppCompatDelegate.getApplicationLocales()[0]?.let { Log.i("MainView" , it.displayLanguage) }
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val dScope = rememberCoroutineScope()
    val dState = rememberDrawerState(initialValue = DrawerValue.Closed)


    //Repl()
    Drawer(scope = dScope, dState = dState)

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PanktiMobTheme {


        MainView()
    }
}