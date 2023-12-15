fun main() {
    val store = Store
    var state: State = State.SelectArchive(store, Menu())
    val screen = Screen()
    while (state !is State.Exit) {
        state = screen.render(state)
    }
}
