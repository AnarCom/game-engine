package ru.nsu.editor.view.component

import javafx.scene.layout.VBox
import ru.nsu.editor.view.utils.*
import ru.nsu.lib.common.EnemyType
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*
import java.lang.NumberFormatException

class EnemySettingsComponent : NamedSettingsComponent<EnemyType>("EnemySettingsComponent") {
    override fun getSettings(): Pair<String, EnemyType> {
        try{
            return Pair(
                name.text,
                EnemyType(
                    health = health.text.toInt(),
                    speed = speed.text.toInt(),
                    file = sprite.text,
                    enemyAttack = damage.text.toInt(),
                )
            )
        } catch(e : NumberFormatException){
            warning("Неверный формат данных", content = e.message)
        }
        return Pair("New_Enemy", EnemyType(0,0,"./",0))
    }


    private var damage = textfield { text = defaultInt }
    private var health = textfield { text = defaultInt }
    private var speed = textfield { text = defaultInt }
    private var sprite = textfield { text = defaultUrl }
    private var name = textfield { text = defaultString }


    override val root = vbox{
        squeezebox {//TODO move to component
            fold("Enemy", expanded = true){
                form{
                    fieldset {
                        field("Name"){
                            add(name)
                        }
                        field("Sprite"){
                            add(sprite)
                        }
                        field("Damage"){
                            add(damage)
                        }
                        field("Speed"){
                            add(speed)
                        }
                        field("Health"){
                            add(health)
                        }
                    }
                }
            }
        }
    }
}
