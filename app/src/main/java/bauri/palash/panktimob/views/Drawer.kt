package bauri.palash.panktimob.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bauri.palash.panktimob.ui.theme.PanktiMobTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    modifier: Modifier = Modifier, scope: CoroutineScope = rememberCoroutineScope(),
    dState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    //val scope = rememberCoroutineScope()
    val dItems = listOf(
        Icons.Default.List,
        Icons.Default.Info
    )
    val selItem = remember { mutableStateOf(dItems[0]) }
    ModalNavigationDrawer(
        drawerState = dState,
        drawerContent = {
            ModalDrawerSheet() {

                Spacer(modifier = Modifier.height(12.dp))
                dItems.forEach { item ->
                    NavigationDrawerItem(label = { Text(item.name) },


                        selected = item == selItem.value,
                        onClick = {
                            scope.launch { dState.close() }
                            selItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = { Icon(item, contentDescription = null) }
                    )
                }

            }
        },
        content = {
            Repl(scope, dState)
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