package ru.nsu.editor.view.enums

import ru.nsu.editor.view.component.*
import ru.nsu.lib.common.EnemyType
import ru.nsu.lib.common.EnemyWave
import ru.nsu.lib.common.TowerData
import kotlin.jvm.Throws
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

enum class LayoutType {
    TOWER {
        override fun buildSettingsComp(preset: Pair<String, Any>?): NamedSettingsComponent<Any> {
            if (preset == null)
                return TowerSettingsComponent()
            if (preset.second is TowerData) {
                @Suppress("UNCHECKED_CAST")
                return TowerSettingsComponent(preset as Pair<String, TowerData>)
            } else {
                throw TypeCastException("Cast error while building component")
            }
        }

        override fun getPresetClass(): KClass<*> {
            return TowerData::class
        }
    },
    ENEMY {
        override fun buildSettingsComp(preset: Pair<String, Any>?): NamedSettingsComponent<Any> {
            if (preset == null)
                return EnemySettingsComponent()
            if (preset.second is EnemyType) {
                @Suppress("UNCHECKED_CAST")
                return EnemySettingsComponent(preset as Pair<String, EnemyType>)
            } else {
                throw TypeCastException("Cast error while building component")
            }
        }

        override fun getPresetClass(): KClass<*> {
            return EnemyType::class
        }
    },
    WAVE {
        override fun buildSettingsComp(preset: Pair<String, Any>?): NamedSettingsComponent<Any> {
            if (preset == null)
                return WaveSettingsComponent()
            if (preset.second is EnemyWave) {
                @Suppress("UNCHECKED_CAST")
                return WaveSettingsComponent(preset as Pair<String, EnemyWave>)
            } else {
                throw TypeCastException("Cast error while building component")
            }
        }

        override fun getPresetClass(): KClass<*> {
            return EnemyWave::class
        }

    };
    //    MAP,
    //    LEVEL;
    @Throws(TypeCastException::class)
    abstract fun buildSettingsComp(preset: Pair<String, Any>? = null): NamedSettingsComponent<Any>
    abstract fun getPresetClass(): KClass<*>
}
