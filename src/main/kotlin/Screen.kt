import java.util.Scanner

class Screen {
    fun render(state: State): State {
        return when (state) {
            is SelectArchive -> {
                selectArchive(state)
            }

            is CreateArchive -> {
                createArchive(state)
            }

            is SelectNote -> {
                selectNote(state)
            }

            is CreateNote -> {
                createNote(state)
            }

            is ShowNote -> {
                showNote(state)
            }

            is Exit -> {
                showExit(state)
            }
        }
    }

    private fun selectArchive(state: SelectArchive): State {
        println(state.message)
        state.menu.handlerState(state)
        menuRender(state.menu.getMenu())
        val commandCode = getMenuSelection(state.menu)
        return state.menu.select(commandCode)
    }

    private fun createArchive(state: CreateArchive): State {
        println(state.message)
        val archiveName = getNonEmptyUserInput()
        state.store.addArchive(archiveName)
        return SelectArchive(state.store, Menu())
    }

    private fun selectNote(state: SelectNote): State {
        println(state.message(state.archive.getName()))
        state.menu.handlerState(state)
        menuRender(state.menu.getMenu())
        val commandCode = getMenuSelection(state.menu)
        return state.menu.select(commandCode)
    }

    private fun createNote(state: CreateNote): State {
        println(state.nameCreationMessage)
        val noteName = getNonEmptyUserInput()
        println(state.contentCreationMessage)
        val noteContent = getNonEmptyUserInput()
        state.store.addNote(state.archive, noteName, noteContent)
        return SelectNote(state.store, Menu(), state.archive)
    }

    private fun showNote(state: ShowNote): State {
        println(state.message(state.note.getName()))
        println(state.note.getContent())
        return SelectNote(state.store, Menu(), state.store.getArchiveByNote(state.note))
    }

    private fun showExit(state: Exit): State {
        println(state.message)
        return state
    }

    private fun menuRender(menu: MutableList<MenuItem>) {
        menu.forEachIndexed { index, menuItem ->
            println("${index}. ${menuItem.title}")
        }
    }

    private fun getMenuSelection(menu: Menu): Int {
        var commandCode = Scanner(System.`in`).nextLine()
        while (menu.validate(commandCode)) {
            println(menu.getError())
            commandCode = Scanner(System.`in`).nextLine()
        }
        return commandCode.toInt()
    }

    private fun getNonEmptyUserInput(): String {
        var userInput = Scanner(System.`in`).nextLine()
        while (userInput.isEmpty()) {
            println("Ввод не может быть пустой строкой, повторите попытку!")
            userInput = Scanner(System.`in`).nextLine()
        }
        return userInput
    }
}
