fun main() {
    val store = Store
    var state: State = SelectArchive(store, Menu())
    val screen = Screen()
    while (state !is Exit) {
        state = screen.render(state)
    }
}
