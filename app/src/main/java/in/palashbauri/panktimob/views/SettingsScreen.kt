package `in`.palashbauri.panktimob.views

import `in`.palashbauri.panktimob.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopMenu(navController: NavController, saveSettings: () -> Unit) {
    val currentContext = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                if (!navController.popBackStack()) {
                    currentContext.findActivity()?.finish()
                }

            },
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back to previous")
        }

        Text(text = stringResource(id = R.string.settings), modifier = Modifier.weight(1f))

        Button(onClick = saveSettings) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(R.string.save_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SsPreview() {
    val tempNavController = rememberNavController()
    SettingsScreen(appCon = LocalContext.current, tempNavController)
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SettingsScreen(appCon: Context, navController: NavController) {

    val bengaliLang = mapOf("name" to stringResource(id = R.string.bengali), "code" to "bn")
    val englishLang = mapOf("name" to stringResource(id = R.string.english), "code" to "en")

    val langs = listOf(
        bengaliLang,
        englishLang
    )


    var expanded by remember {
        mutableStateOf(false)
    }

    var selLang by remember {
        mutableStateOf("")
    }

    var selLangName by remember {
        mutableStateOf(AppCompatDelegate.getApplicationLocales()[0]?.displayName ?: "")
    }


    var tFSize by remember {
        mutableStateOf(Size.Zero)
    }



    Column(modifier = Modifier.fillMaxSize()) {

        SettingsTopMenu(navController) {
            changeLanguage(appCon, selLang)
        }
        Spacer(modifier = Modifier.size(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.language), modifier = Modifier.weight(0.5F))
            Row(modifier = Modifier.weight(0.5F)) {
                //Text(text = "Language" , modifier = Modifier.weight(0.5F))

                OutlinedTextField(
                    value = selLangName,
                    onValueChange = { selLangName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            tFSize = coordinates.size.toSize()
                        },
                    label = { AppCompatDelegate.getApplicationLocales()[0]?.let { Text(it.displayName) } },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, "contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )

                DropdownMenu(expanded = expanded,
                    modifier = Modifier.width(with(LocalDensity.current) { tFSize.width.toDp() }),
                    onDismissRequest = { expanded = false }) {
                    langs.forEach { l ->
                        DropdownMenuItem(text = {
                            Text(text = l["name"].toString())
                        }, onClick = {
                            expanded = false
                            selLang = l["code"].toString()
                            selLangName = l["name"].toString()


                        })
                    }
                }
            }
        }

        ////
    }
}


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun changeLanguage(context: Context, language: String) {
    context.findActivity()?.runOnUiThread {
        val appLocale = LocaleListCompat.forLanguageTags(language)
        AppCompatDelegate.setApplicationLocales(appLocale)
        //context.findActivity()?.recreate()
    }

    //context.findActivity()?.recreate()
}

