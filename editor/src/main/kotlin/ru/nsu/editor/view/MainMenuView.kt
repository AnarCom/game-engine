фвpackage ru.nsu.editor.view

import ru.nsu.lib.Library
import tornadofx.*

class MainMenuView : View() {

    override val root = vbox {
        button("${Library().someLibraryMethod()}")
    }
}
