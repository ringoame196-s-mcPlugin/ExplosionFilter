package com.github.ringoame196_s_mcPlugin

import org.bukkit.Material
import org.bukkit.block.Block

object ExplosionFilter {
    private val filterBlockList = mutableSetOf(Material.GLASS)

    // 爆発から保護するブロックを破壊対象リストから除外
    fun removeFilterBlock(blockList: MutableList<Block>) {
        blockList.removeIf {
            it.type in filterBlockList
        }
    }

    fun getFilterBlockList(): Set<Material> {
        return filterBlockList
    }

    fun save(newFilterBlockList: Set<Material>) {
        filterBlockList.clear()
        filterBlockList.addAll(newFilterBlockList)
    }
}
