package ru.nsu.editor.view.component

import javafx.scene.layout.VBox
import ru.nsu.editor.view.utils.*
import ru.nsu.lib.common.EnemyType
import ru.nsu.lib.common.TowerUpdate
import tornadofx.*
import java.lang.NumberFormatException

class EnemySettingsComponent(
    override val preset: Pair<String, EnemyType> = Pair(
        defaultString,
        EnemyType(0, 0, defaultUrl, 0)
    )
) : NamedSettingsComponent<EnemyType>("EnemySettingsComponent") {
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


    private var damage = textfield { text = data.enemyAttack.toString() }
    private var health = textfield { text = data.health.toString() }
    private var speed = textfield { text = data.speed.toString() }
    private var sprite = textfield { text = data.file }
    private var name = textfield { text = filename }


    override val root = vbox{
        form {
            fieldset {
                field("Enemy name") {
                    add(name)
                }
            }
        }
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
