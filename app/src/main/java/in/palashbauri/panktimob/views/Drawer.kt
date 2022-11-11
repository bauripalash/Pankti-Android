package `in`.palashbauri.panktimob.views

import `in`.palashbauri.panktimob.R
import `in`.palashbauri.panktimob.Route
import `in`.palashbauri.panktimob.ui.theme.PanktiMobTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    scope: CoroutineScope = rememberCoroutineScope(),
    dState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    val navCtrl = rememberNavController()


    //val scope = rememberCoroutineScope()

    var selItem by remember {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        drawerState = dState,
        drawerContent = {

            ModalDrawerSheet {

                Card(
                    shape = RoundedCornerShape(16F),
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Pankti Image"
                        )
                        //Text(text = "PANKTI")
                    }

                }
                Spacer(modifier = Modifier.height(12.dp))
                //dItems.forEach { item ->
                NavigationDrawerItem(label = { Text(stringResource(id = R.string.editor)) },


                    selected = selItem == 1,
                    onClick = {
                        scope.launch { dState.close() }
                        navCtrl.navigate("Editor") {
                            popUpTo(navCtrl.graph.startDestDisplayName)
                            //launchSingleTop = true
                        }
                        selItem = 1


                        //selItem.value =
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_edit_icon),
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                NavigationDrawerItem(label = { Text(text = stringResource(id = R.string.repl)) },


                    selected = selItem == 0,
                    onClick = {
                        scope.launch { dState.close() }
                        navCtrl.navigate("Repl") {
                            popUpTo(navCtrl.graph.startDestDisplayName)
                            //launchSingleTop(true)
                        }

                        selItem = 0

                        //selItem.value =
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_quick),
                            contentDescription = null
                        )
                    }
                )
                //}
                Spacer(modifier = Modifier.height(12.dp))
                NavigationDrawerItem(label = { Text(text = "Settings") },


                    selected = selItem == 2,
                    onClick = {
                        scope.launch { dState.close() }
                        navCtrl.navigate("Settings") {
                            popUpTo(navCtrl.graph.startDestDisplayName)
                            //launchSingleTop(true)
                        }

                        selItem = 2

                        //selItem.value =
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_menubook),
                            contentDescription = null
                        )
                    }
                )

            }
        },
        content = {
            //TopMenu(scope, dState )
            NavHost(navController = navCtrl, startDestination = Route.Repl.route) {
                composable(route = Route.Repl.route) {
                    Repl(scope = scope, dState = dState)
                }
                composable(route = Route.Editor.route) {
                    EditorFragment(scope, dState)
                }
                
                composable(route = Route.Settings.route){
                    SettingsScreen(appCon = LocalContext.current)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DrawerPreview() {
    PanktiMobTheme {
        Drawer()
    }

}