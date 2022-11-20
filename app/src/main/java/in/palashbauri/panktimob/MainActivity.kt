package `in`.palashbauri.panktimob


import `in`.palashbauri.panktimob.ui.theme.PanktiMobTheme
import `in`.palashbauri.panktimob.views.Drawer
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lang = AppCompatDelegate.getApplicationLocales().get(0)?.toLanguageTag()
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
        val shouldBeDark = GetBoolPref(this , getString(R.string.darkPref))
        if (shouldBeDark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContent {
            PanktiMobTheme(darkTheme = isNightMode()) {
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
fun isNightMode() = when (AppCompatDelegate.getDefaultNightMode()) {
    AppCompatDelegate.MODE_NIGHT_NO -> false
    AppCompatDelegate.MODE_NIGHT_YES -> true
    else -> isSystemInDarkTheme()
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun SetBoolPref(context: Context , key : String , value : Boolean){
    context.findActivity()?.runOnUiThread {
        val sharedPreferences = context.findActivity()?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            with (sharedPreferences.edit()){
                putBoolean(key, value)
                apply()
            }
        }
    }
}

fun GetBoolPref(context: Context , key : String) : Boolean {
    val sharedPreferences = context.findActivity()?.getPreferences(Context.MODE_PRIVATE)?:return false

    return sharedPreferences.getBoolean(key , false)

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val dScope = rememberCoroutineScope()
    val dState = rememberDrawerState(initialValue = DrawerValue.Closed)


    //Repl() as Default
    Drawer(scope = dScope, dState = dState)

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PanktiMobTheme {


        MainView()
    }
}