class Screen {
    fun render(state: State): State {
        return when (state) {
            is State.SelectArchive -> {
                selectArchive(state)
            }

            is State.CreateArchive -> {
                createArchive(state)
            }

            is State.SelectNote -> {
                selectNote(state)
            }

            is State.CreateNote -> {
                createNote(state)
            }

            is State.ShowNote -> {
                showNote(state)
            }

            is State.Exit -> {
                showExit(state)
            }
        }
    }

    private fun selectArchive(state: State.SelectArchive): State {
        println(state.message)
        state.menu.handlerState(state)
        menuRender(state.menu.getMenu())
        val commandCode = getMenuSelection(state.menu)
        return state.menu.select(commandCode)
    }

    private fun createArchive(state: State.CreateArchive): State {
        println(state.message)
        val archiveName = getNonEmptyUserInput()
        state.store.addArchive(archiveName)
        return State.SelectArchive(state.store, Menu())
    }

    private fun selectNote(state: State.SelectNote): State {
        println(state.message(state.archive.name))
        state.menu.handlerState(state)
        menuRender(state.menu.getMenu())
        val commandCode = getMenuSelection(state.menu)
        return state.menu.select(commandCode)
    }

    private fun createNote(state: State.CreateNote): State {
        println(state.nameCreationMessage)
        val noteName = getNonEmptyUserInput()
        println(state.contentCreationMessage)
        val noteContent = getNonEmptyUserInput()
        state.store.addNote(state.archive, noteName, noteContent)
        return State.SelectNote(state.store, Menu(), state.archive)
    }

    private fun showNote(state: State.ShowNote): State {
        val (name, content) = state.note
        println(state.message(name))
        println(content)
        return State.SelectNote(state.store, Menu(), state.store.getArchiveByNote(state.note))
    }

    private fun showExit(state: State.Exit): State {
        println(state.message)
        return state
    }

    private fun menuRender(menu: MutableList<MenuItem>) {
        menu.forEachIndexed { index, menuItem ->
            println("${index}. ${menuItem.title}")
        }
    }

    private fun getMenuSelection(menu: Menu): Int {
        var commandCode = readln()
        while (menu.validate(commandCode)) {
            println(menu.getError())
            commandCode = readln()
        }
        return commandCode.toInt()
    }

    private fun getNonEmptyUserInput(): String {
        var userInput = readln()
        while (userInput.trim().isEmpty()) {
            println("Ввод не может быть пустой строкой, повторите попытку!")
            userInput = readln()
        }
        return userInput
    }
}
