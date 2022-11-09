package bauri.palash.panktimob

sealed class Route(val route: String) {
    object Repl : Route("Repl")
    object Editor : Route("Editor")
}
