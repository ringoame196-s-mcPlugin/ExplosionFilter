package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.collections.set

object FilterGUIManager {
    private val guiName = "${ChatColor.DARK_BLUE}フィルターブロック"
    private const val GUI_SIZE = 54
    private const val PAGE_SIZE = GUI_SIZE - 1
    private val playerPageMap = mutableMapOf<UUID, Int>()

    private val nextItem = ItemStack(Material.ARROW).apply {
        itemMeta = itemMeta.apply {
            setDisplayName("${ChatColor.GREEN}Next")
        }
    }

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

    fun open(player: Player) {
        setPage(player, 0)

        val inventory = Bukkit.createInventory(null, GUI_SIZE, guiName)
        applyFilterBlockList(inventory, 0)
        player.openInventory(inventory)
    }

    fun updatePage(inventory: Inventory, player: Player) {
        val page = nextPage(player)
        applyFilterBlockList(inventory, page)
    }

    private fun applyFilterBlockList(inventory: Inventory, page: Int) {
        inventory.clear()
        val filterList = ExplosionFilter.getFilterBlockList()
        val start = page * PAGE_SIZE

        if (filterList.isNotEmpty()) {
            filterList
                .drop(start)
                .take(PAGE_SIZE)
                .forEach {
                    inventory.addItem(ItemStack(it))
                }
        }
        inventory.setItem(GUI_SIZE - 1, nextItem) // 最後にnextボタンを追加
    }

    fun isFilterGUI(inventory: InventoryView): Boolean {
        return inventory.title == guiName
    }

    fun isNextItem(item: ItemStack): Boolean {
        return item == nextItem
    }

    // GUIの内容をフィルターブロック設定として保存
    fun saveFilterBlockList(inventory: Inventory) {
        val newFilterBlockList = inventory
            .filterNotNull()
            .filterNot(::isNextItem)
            .map(ItemStack::getType)
            .toSet()
        ExplosionFilter.save(newFilterBlockList)
    }
}
