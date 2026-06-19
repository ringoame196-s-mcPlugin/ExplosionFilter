package com.github.ringoame196_s_mcPlugin.config

import org.bukkit.plugin.java.JavaPlugin

object ConfigManager {
    lateinit var plugin: JavaPlugin
    private val config get() = plugin.config
    private const val FILTER_BLOCKS_KEY = "filter-blocks"

    fun getFilterBlocks(): List<String> {
        return config.getStringList(FILTER_BLOCKS_KEY)
    }

    fun saveFilterBlocks(filterBlockList: List<String>) {
        config.set(FILTER_BLOCKS_KEY, filterBlockList)
        plugin.saveConfig()
    }
}
