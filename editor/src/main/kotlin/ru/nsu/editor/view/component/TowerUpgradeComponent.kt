package ru.nsu.editor.view.component

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import org.w3c.dom.Node
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*
import java.awt.TextField
import java.lang.NumberFormatException

class TowerUpgradeComponent(index:Int) : SettingsComponent2<TowerUpdate>("TowerSettingsComponent") {//: SettingsComponent<TowerUpdate>, View("TowerUpgradeComponent")
//    override val titleProperty: StringProperty = SimpleStringProperty("TowerSettingsComponent")
    override fun getSettings(): TowerUpdate {
        try{
            return TowerUpdate(
                enemyDamage = damage.text.toInt(),
                shootingSpeed = speed.text.toInt(),
                maxEnemyCount = maxEnemyCount.text.toInt(),
                radius = radius.text.toInt(),
                cost = cost.text.toInt(),
                removeMoneyCashback = sell.text.toInt()
            )
        } catch(e : NumberFormatException){
            warning("Неверный формат данных", content = e.message)
        }
        return TowerUpdate(0,0,0,0,0,0)
    }

//    private fun toIntValid(tf : javafx.scene.control.TextField): Int {
//        try {
//            return tf.text.toInt()
//        } catch (e: NumberFormatException){
//
//        }
//        return 0
//    } TODO: валидация и покраска неправильных полей.
    /*    private fun createTextField(): javafx.scene.control.TextField {
        return textfield() {
            text = defaultInt
            textProperty().addListener{obs, old, new ->
                if(!regexNum.matches(new)){
                    println(obs)
                }
            }
        }
    }

    private val regexNum = Regex("^[0-9]+\$")
    */

    private val defaultInt = "0"

    private var damage = textfield { text = defaultInt }
    private var speed = textfield { text = defaultInt }
    private var radius = textfield { text = defaultInt }
    private var maxEnemyCount = textfield { text = defaultInt }
    private var cost = textfield { text = defaultInt }
    private var sell = textfield { text = defaultInt }

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
        squeezebox {//TODO move to commponent
            fold("Attack", expanded = false){
                form{
                    fieldset {
                        field("Type"){
                            combobox<TowerEditorView.TowerType>{//TODO: No field
                                items= FXCollections.observableArrayList(*TowerEditorView.TowerType.values())
                            }
                        }
//                                    if(towerData.updates[index]) TODO: towertype == AOE -> add field aoe radius || else - max attack enemies
                        field("Damage"){
                            add(damage)
                        }
                        field("Speed"){
                            add(speed)
                        }
                        field("Radius"){
                            add(radius)
                        }
                        field("Max enemy to hit"){
                            add(maxEnemyCount)
                        }
                    }
                }
            }
            fold("Economy", expanded = false){
                form{
                    fieldset {
                        if(index == 0){
                            field("Initial"){
                                add(cost)
                            }
                        }
                        else{
                            field("Upgrade"){
                                add(cost)
                            }
                        }
                        field("Sell"){
                            add(sell)
                        }

                    }
                }
            }
        }
    }
}