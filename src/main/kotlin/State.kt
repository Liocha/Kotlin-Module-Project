sealed class State(val store: Store, val menu: Menu) {
    class SelectArchive(
        store: Store,
        menu: Menu
    ) : State(store, menu) {
        val message: String = "Список архивов:"
    }

    class CreateArchive(
        store: Store,
        menu: Menu
    ) : State(store, menu) {
        val message: String = "Введите название архива и нажмите enter"
    }

    class SelectNote(
        store: Store,
        menu: Menu,
        val archive: Archive
    ) : State(store, menu) {
        fun message(name: String): String {
            return "Список заметок в архиве '${name}':"
        }
    }

    class CreateNote(
        store: Store,
        menu: Menu,
        val archive: Archive
    ) : State(store, menu) {
        val nameCreationMessage: String = "Введите название заметки и нажмите enter"
        val contentCreationMessage: String = "Введите содержимое заметки нажмите enter"
    }

    class ShowNote(
        store: Store,
        menu: Menu,
        val note: Note,
    ) : State(store, menu) {
        fun message(name: String): String {
            return "Содержимое заметки c именем '${name}':"
        }
    }

    class Exit(
        store: Store,
        menu: Menu
    ) : State(store, menu) {
        val message: String = "ВСЕГО ХОРОШЕГО!!!"
    }
}