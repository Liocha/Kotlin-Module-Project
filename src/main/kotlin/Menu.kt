class MenuItem(val title: String, val state: State)

class Menu {
    private val menu = mutableListOf<MenuItem>()
    private var lastError = ""

    fun handlerState(state: State) {
        when (state) {
            is State.SelectArchive -> {
                buildArchiveMenu(state.store)
            }

            is State.SelectNote -> {
                buildNotesMenu(state.store, state.archive)
            }

            else -> {}
        }
    }

    private fun buildArchiveMenu(store: Store) {
        menu.add(MenuItem("Создать архив", State.CreateArchive(store, Menu())))
        store.archives.forEach { element ->
            menu.add(
                MenuItem(
                    element.name,
                    State.SelectNote(store, Menu(), element)
                )
            )
        }
        menu.add(MenuItem("Выйти", State.Exit(store, Menu())))
    }

    private fun buildNotesMenu(store: Store, archive: Archive) {
        menu.add(MenuItem("Создать заметку", State.CreateNote(store, Menu(), archive)))
        store.getNotes(archive).forEach { element ->
            menu.add(
                MenuItem(
                    element.name,
                    State.ShowNote(store, Menu(), element)
                )
            )
        }
        menu.add(MenuItem("Назад", State.SelectArchive(store, Menu())))
    }

    fun getMenu(): MutableList<MenuItem> {
        return menu
    }

    fun select(id: Int): State {
        return menu[id].state
    }

    fun validate(commandCode: String): Boolean {
        if (commandCode.isEmpty()) {
            lastError = "Cледует вводить цифру"
            return true
        }

        for (char in commandCode.toCharArray()) {
            if (!char.isDigit()) {
                lastError = "Cледует вводить цифру"
                return true
            }
        }

        val correctRange = 0 until menu.size
        if (commandCode.toInt() in correctRange) {
            return false
        }
        lastError = "Такой цифры нет, введите корректный символ"
        return true
    }

    fun getError(): String {
        return lastError
    }
}
