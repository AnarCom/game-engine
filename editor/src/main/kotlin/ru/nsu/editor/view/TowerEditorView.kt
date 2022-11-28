package ru.nsu.editor.view

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.SkinBase
import ru.nsu.lib.common.TowerData
import ru.nsu.lib.common.TowerUpdate

import tornadofx.*
import java.io.File

class TowerEditorView : View("Tower editor") {
    private var towerData = TowerData("",
        arrayOf(
            TowerUpdate(0, 0, 0, 0, 0, 0),
            TowerUpdate(0, 0, 0, 0, 0, 0)
        )
    )

    init {
        val mapper = jacksonObjectMapper()
//        mapper.writeValue(
//            File("./configuration/test.json"),
//            TowerData
//        )
    }

    override val root = borderpane {
        top{
            useMaxWidth = true
            hbox{
                button("Back").action {
                    replaceWith<MainMenuView>()
                }
                button("Save").action {

                }
                button("Import")
            }
        }
        left {
            button("Left")
        }
        right {
            vbox {
//                text("OPTIONS")
//                line()
                squeezebox {
                    fold("Visuals", expanded = true){
                        form {
                            fieldset {
                                field("Tower sprite"){
                                    textfield(towerData.file)
                                }
                                field("Projectile sprite TBD"){
                                    textfield()
                                }
                            }

                        }
                    }
//                    for((element, index) in towerData )
                }
                towerData.updates.forEachIndexed{ index, element ->
                    borderpane {

                        left = hbox{
                            text("Upgrade #$index"){
                                hboxConstraints {
                                    marginTopBottom(15.0)
                                }
                            }
                        }
                        right = hbox{
                            button("Remove"){
                                hboxConstraints {
                                    margin = Insets(10.0)
                                }
                            }
                        }
                    }
                    squeezebox {//TODO move to commponent
                        fold("Attack", expanded = true){
                            form{
                                fieldset {
                                    field("Type"){
                                        combobox<TowerType>{//TODO: No field
                                            items=FXCollections.observableArrayList(*TowerType.values())
                                        }
                                    }
                                    field("Damage"){
                                        textfield {
                                            textProperty().addListener { obs, old, new ->
                                                println("You typed: $new")
                                            }
                                        }
                                    }
                                    field("Attack speed"){
                                        textfield(){
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    enum class TowerType{
        AOE, TARGET
    }
}