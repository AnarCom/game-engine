package ru.nsu.editor.view.component

import javafx.scene.layout.VBox
import ru.nsu.lib.common.EnemyType
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*
import java.lang.NumberFormatException

class EnemySettingsComponent : NamedSettingsComponent<EnemyType>("EnemySettingsComponent") {
    override fun getSettings(): Pair<String, EnemyType> {
        try{
            return Pair("",
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

    private val defaultInt = "0"
    private val defaultPath = "./content"
    private var damage = textfield { text = defaultInt }
    private var health = textfield { text = defaultInt }
    private var speed = textfield { text = defaultInt }
    private var sprite = textfield { text = defaultPath }


    override val root = vbox{
        squeezebox {//TODO move to component
            fold("Enemy", expanded = true){
                form{
                    fieldset {
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
