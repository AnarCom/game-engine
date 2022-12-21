package ru.nsu.editor.view.enums

import ru.nsu.editor.view.component.EnemySettingsComponent
import ru.nsu.editor.view.component.NamedSettingsComponent
import ru.nsu.editor.view.component.SettingsComponent
import ru.nsu.editor.view.component.TowerSettingsComponent
import ru.nsu.lib.common.EnemyType
import ru.nsu.lib.common.TowerData
import kotlin.jvm.Throws
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

enum class LayoutType{
    TOWER {
        override fun <T:Any>buildSettingsComp(preset: Pair<String, T>): NamedSettingsComponent<Any> {
            if(preset.second is TowerData){
                @Suppress("UNCHECKED_CAST")
                return TowerSettingsComponent(preset as Pair<String, TowerData>)
            } else{
                throw TypeCastException("Cast error while building component")
            }
        }

        override fun getPresetClass(): KClass<*> {
            return TowerData::class
        }
    };
//    ENEMY {
//        override fun buildSettingsComp(preset: Pair<String, Any>): NamedSettingsComponent<Any> {
//            if(preset.second is EnemySettingsComponent){
//                @Suppress("UNCHECKED_CAST")
//                return EnemySettingsComponent(preset as Pair<String, EnemyType>)
//            } else{
//                throw TypeCastException("Cast error while building component")
//            }
//        }
//
//        override fun getSettingsClass(): KClass<*> {
//            return TowerSettingsComponent::class
//        }
//    };
//    MAP,
//    WAVE;
    @Throws(TypeCastException::class)
    abstract fun <T: Any>buildSettingsComp(preset: Pair<String, T>): NamedSettingsComponent<Any>
    abstract fun getPresetClass(): KClass<*>
}
