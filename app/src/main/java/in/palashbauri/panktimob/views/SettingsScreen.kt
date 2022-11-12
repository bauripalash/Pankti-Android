package `in`.palashbauri.panktimob.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import `in`.palashbauri.panktimob.R
import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.content.getSystemService
import androidx.core.os.LocaleListCompat
import java.util.Locale

sealed class Language(val name : String , val code : String){
    object Bengali : Language(name = "Bengali" , code = "bn")
    object English : Language(name = "English" , code = "en")
}

val langs = listOf(
    Language.English,
    Language.Bengali
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopMenu() {
    
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                /* TODO */

            },
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Open Drawer")
        }

        Text(text = "Settings", modifier = Modifier.weight(1f))

        Button(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(R.string.save_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SsPreview(){
    SettingsScreen(appCon = LocalContext.current , currentComposer)
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SettingsScreen(appCon : Context , curcom : Composer){
    AppCompatDelegate.getApplicationLocales()[0]?.let { Log.i( "SettingsScreenBegin" , it.displayLanguage) }
    var expanded by remember {
        mutableStateOf(false)
    }

    var selLang by remember{
        mutableStateOf("")
    }

    var TFSize by remember {
        mutableStateOf(Size.Zero)
    }

    val langId by remember {
        mutableStateOf(R.string.code_input_hint)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        //SettingsTopMenu()
        Button(onClick = { changeLanguage(appCon , selLang) }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = appCon.getString(R.string.save_button))
        }
        Spacer(modifier = Modifier.size(5.dp))
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp) ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
            Text(text = appCon.getString(R.string.save_button) , modifier = Modifier.weight(0.5F))
            Row(modifier = Modifier.weight(0.5F)) {
                //Text(text = "Language" , modifier = Modifier.weight(0.5F))

                OutlinedTextField(
                    value = selLang,
                    onValueChange = { selLang = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            TFSize = coordinates.size.toSize()
                        },
                    label = { Text(appCon.getText(R.string.code_output_hint).toString()) },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, "contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )

                DropdownMenu(expanded = expanded,
                    modifier = Modifier.width(with(LocalDensity.current) { TFSize.width.toDp() }),
                    onDismissRequest = { expanded = false }) {
                    langs.forEach { l ->
                        DropdownMenuItem(text = {
                            Text(text = l.name)
                        }, onClick = {
                            expanded = false
                            selLang = l.name



                            //val activity = appCon as Activity

                            //activity.runOnUiThread {  }

                            //Handler(Looper.getMainLooper()).post {
                                //val appLocale = LocaleListCompat.forLanguageTags("bn")
                                //AppCompatDelegate.setApplicationLocales(appLocale)
                                //activity.recreate()
                            //currentComposer.composition.recompose()
                            //println(curcom.composition.recompose())
                            //changeLanguage(appCon , l.code)

                            //}

                            Log.d("DropLang" , AppCompatDelegate.getApplicationLocales().toLanguageTags())

                        })
                    }
                }
            }
        }

        ////
    }
}

fun Context.findActivity() : Activity? = when(this){
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun changeLanguage( context: Context , language: String ){
    context.findActivity()?.runOnUiThread {
        val appLocale = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)
        //context.findActivity()?.recreate()
    }

    //context.findActivity()?.recreate()
}

