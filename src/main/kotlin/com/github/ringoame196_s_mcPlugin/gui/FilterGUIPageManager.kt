package com.github.ringoame196_s_mcPlugin.gui

import org.bukkit.entity.Player
import java.util.UUID

object FilterGUIPageManager {
    private val playerPageMap = mutableMapOf<UUID, Int>()

    fun getPage(player: Player): Int {
        return playerPageMap[player.uniqueId] ?: 0
    }

    fun nextPage(player: Player): Int {
        val page = getPage(player) + 1
        setPage(player, page)
        return page
    }

    fun setPage(player: Player, page: Int) {
        playerPageMap[player.uniqueId] = page
    }

    fun removePlayer(player: Player) {
        playerPageMap.remove(player.uniqueId)
    }
}
