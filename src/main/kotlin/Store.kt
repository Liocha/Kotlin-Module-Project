class Archive(var name: String) {
    val notes = mutableListOf<Note>()

    fun addNote(name: String, content: String) {
        notes.add(Note(name, content))
    }
}

data class Note(val name: String, val content: String)

object Store {
    val archives = mutableListOf<Archive>()

    fun addArchive(name: String) {
        archives.add(Archive(name))
    }

    fun addNote(archive: Archive, name: String, content: String) {
        archive.addNote(name, content)
    }

    fun getNotes(archive: Archive): MutableList<Note> {
        return archive.notes
    }

    fun getArchiveByNote(note: Note): Archive {
        return archives.find { it.notes.contains(note) }!!
    }
}
