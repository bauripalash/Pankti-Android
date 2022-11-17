package `in`.palashbauri.panktimob.views

import `in`.palashbauri.panktimob.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu(scope: CoroutineScope, dState: DrawerState, navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                if (dState.isClosed) {
                    scope.launch { dState.open() }
                } else if (dState.isOpen) {
                    scope.launch { dState.close() }
                }

            },
        ) {
            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
        }

        Text(text = stringResource(id = R.string.pankti), modifier = Modifier.weight(1f))

        IconButton(onClick = {
            navController.navigate("Settings") {
                popUpTo(navController.graph.startDestDisplayName)
                //launchSingleTop(true)
            }
        }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}