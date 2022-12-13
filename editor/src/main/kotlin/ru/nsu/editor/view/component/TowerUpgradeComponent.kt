package ru.nsu.editor.view.component

import javafx.collections.FXCollections
import javafx.geometry.Insets
import ru.nsu.editor.view.TowerEditorView
import tornadofx.*

class TowerUpgradeComponent(index:Int) : View("TowerUpgradeComponent") {
    override val root = vbox {
        borderpane {
            left = hbox{
                text("Upgrade #$index"){
                    hboxConstraints {
                        marginTopBottom(15.0)
                    }
                }
            }
        }
        squeezebox {//TODO move to component
            fold("Attack", expanded = false){
                form{
                    fieldset {
                        /*field("Type"){
                            combobox<TowerEditorView.TowerType>{//TODO: No field
                                items= FXCollections.observableArrayList(*TowerEditorView.TowerType.values())
                            }
                        }
                                    if(towerData.updates[index]) TODO: towertype == AOE -> add field aoe radius || else - max attack enemies*/
                        field("Damage"){
                            textfield {
                                textProperty().addListener { obs, old, new ->
                                    println("You typed: $new")
                                }
                            }
                        }
                        field("Speed"){
                            textfield(){
                            }
                        }
                        field("Radius"){
                            textfield(){
                            }
                        }
                        field("Max enemy to hit"){
                            textfield(){
                            }
                        }
                    }
                }
            }
            fold("Economy", expanded = false){
                form{
                    fieldset {
                        if(index == 0){
                            field("Initial"){
                                textfield(){}
                            }
                        }
                        else{
                            field("Upgrade"){
                                textfield(){}
                            }
                        }
                        field("Sell"){
                            textfield(){}
                        }

                    }
                }
            }
        }
    }
}