package `in`.palashbauri.panktimob.views

import `in`.palashbauri.panktimob.R
import `in`.palashbauri.panktimob.Route
import `in`.palashbauri.panktimob.ui.theme.PanktiMobTheme
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    modifier: Modifier = Modifier, scope: CoroutineScope = rememberCoroutineScope(),
    dState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    val navCtrl = rememberNavController()


    //val scope = rememberCoroutineScope()
    val dItems = listOf(
        Icons.Default.List,
        Icons.Default.Info
    )
    val selItem = remember { mutableStateOf(dItems[0]) }
    ModalNavigationDrawer(
        drawerState = dState,
        drawerContent = {

            ModalDrawerSheet {

                Card(shape = RoundedCornerShape(16F),
                modifier = Modifier
                    .fillMaxWidth()

                    ) {
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                        ) {
                        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Pankti Image")
                        //Text(text = "PANKTI")
                    }

                }
                Spacer(modifier = Modifier.height(12.dp))
                //dItems.forEach { item ->
                NavigationDrawerItem(label = { Text("Editor") },


                    selected = false,
                    onClick = {
                        scope.launch { dState.close() }
                        navCtrl.navigate("Editor") {
                            popUpTo(navCtrl.graph.startDestDisplayName)
                            //launchSingleTop = true
                        }


                        //selItem.value =
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
                Spacer(modifier = Modifier.height(12.dp))
                NavigationDrawerItem(label = { Text("Repl") },


                    selected = false,
                    onClick = {
                        scope.launch { dState.close() }
                        navCtrl.navigate("Repl") {
                            popUpTo(navCtrl.graph.startDestDisplayName)
                            //launchSingleTop(true)
                        }

                        //selItem.value =
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
                )
                //}

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