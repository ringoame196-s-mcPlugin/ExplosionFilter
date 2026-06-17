package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.ExplosionFilter
import com.github.ringoame196_s_mcPlugin.FilterGUIManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class Events : Listener {
    @EventHandler
    fun onEntityExplode(e: EntityExplodeEvent) {
        ExplosionFilter.removeFilterBlock(e.blockList())
    }

    @EventHandler
    fun onFilterGUIClick(e: InventoryClickEvent) {
        val gui = e.view
        val inventory = e.inventory
        val player = e.whoClicked as? Player ?: return
        val item = e.currentItem ?: return
        if (!FilterGUIManager.isFilterGUI(gui)) return

        // nextアイテムで次のページへ移動
        if (FilterGUIManager.isNextItem(item)) {
            e.isCancelled = true
            FilterGUIManager.nextApplicationFilterBlockList(inventory, player)
        }
    }

    @EventHandler
    fun onFilterGUIClose(e: InventoryCloseEvent) {
        val gui = e.view
        val inventory = e.inventory
        if (!FilterGUIManager.isFilterGUI(gui)) return
        FilterGUIManager.saveFilterBlockList(inventory) // GUIの内容をフィルターブロック設定として保存
    }
}
