package ru.nsu.editor.view.utils

import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import tornadofx.*
import java.io.File
import java.net.URI

fun getFilenameFromURI(uri: String): String {
    return uri
        .substring(
            startIndex = uri.lastIndexOf("/") + 1
        ).takeWhile {
            it != '.'
        }
}

fun getFilenames(uri: URI): ArrayList<String>{
    val filenames: ArrayList<String> = arrayListOf()
    File(uri).list()?.forEach { filenames.add(getFilenameFromURI(it)) }
    return filenames
}