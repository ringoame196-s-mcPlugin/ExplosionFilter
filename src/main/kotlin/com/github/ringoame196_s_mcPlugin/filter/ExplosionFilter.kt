package com.github.ringoame196_s_mcPlugin.filter

import com.github.ringoame196_s_mcPlugin.config.ConfigManager
import org.bukkit.Material
import org.bukkit.block.Block

object ExplosionFilter {
    private val filterBlockList = mutableSetOf<Material>()

    fun loadFilterBlockList() {
        filterBlockList.addAll(
            ConfigManager.getFilterBlocks().mapNotNull {
                Material.getMaterial(it)
            }
        )
    }

    // 爆発から保護するブロックを破壊対象リストから除外
    fun removeFilterBlock(blockList: MutableList<Block>) {
        blockList.removeIf {
            it.type in filterBlockList
        }
    }

    fun getFilterBlockList(): Set<Material> = filterBlockList.toSet()

    fun save(newFilterBlockList: Collection<Material>) {
        filterBlockList.clear()
        filterBlockList.addAll(newFilterBlockList)

        ConfigManager.saveFilterBlocks(
            newFilterBlockList.map { it.name }
        )
    }
}