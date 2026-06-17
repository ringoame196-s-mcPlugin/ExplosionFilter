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
    private val selectNumberMap = mutableMapOf<UUID, Int>()

    private val nextItem = ItemStack(Material.ARROW).apply {
        itemMeta = itemMeta.apply {
            setDisplayName("${ChatColor.GREEN}Next")
        }
    }

    fun open(player: Player) {
        val inventory = makeInventory()
        selectNumberMap[player.uniqueId] = 0 // プレイヤーが現在開いているページを持っておく
        player.openInventory(inventory)
    }

    private fun makeInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, GUI_SIZE, guiName)
        applicationFilterBlockList(inventory, 0)
        return inventory
    }

    fun nextApplicationFilterBlockList(inventory: Inventory, player: Player) {
        // プレイヤーが現在開いているページを更新
        val page = selectNumberMap.getOrDefault(player.uniqueId, 0) + 1
        selectNumberMap[player.uniqueId] = page

        applicationFilterBlockList(inventory, page)
    }

    private fun applicationFilterBlockList(inventory: Inventory, selectNumber: Int) {
        inventory.clear()
        val filterList = ExplosionFilter.getFilterBlockList()
        val start = selectNumber * PAGE_SIZE

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
        val newFilterBlockList = mutableSetOf<Material>()

        for (item in inventory.filterNotNull().filter { !isNextItem(it) }) {
            newFilterBlockList.add(item.type)
        }
        ExplosionFilter.save(newFilterBlockList)
    }
}
