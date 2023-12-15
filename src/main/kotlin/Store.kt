class Archive(private var name: String) {
    private val notes = mutableListOf<Note>()

    fun getName(): String {
        return name
    }

    fun addNote(name: String, content: String) {
        put(Note(name, content))
    }

    private fun put(note: Note) {
        notes.add(note)
    }

    fun getNotes(): MutableList<Note> {
        return notes
    }

    override fun toString(): String {
        return name
    }
}

class Note(private var name: String, private val content: String) {
    fun getContent(): String {
        return content
    }

    fun getName(): String {
        return name
    }

    override fun toString(): String {
        return name
    }
}

object Store {
    val archives = mutableListOf<Archive>()

    fun addArchive(name: String) {
        archives.add(Archive(name))
    }

    fun addNote(archive: Archive, name: String, content: String) {
        archive.addNote(name, content)
    }

    fun getNotes(archive: Archive): MutableList<Note> {
        return archive.getNotes()
    }

    fun getArchiveByNote(note: Note): Archive {
        return archives.find { it.getNotes().contains(note) }!!
    }
}
