package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.events.ExplosionEvents
import com.github.ringoame196_s_mcPlugin.events.FilterGUIEvents
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()

        ConfigManager.plugin = plugin
        ExplosionFilter.loadFilterBlockList()

        server.pluginManager.registerEvents(FilterGUIEvents(), plugin)
        server.pluginManager.registerEvents(ExplosionEvents(), plugin)

        val command = getCommand("explosionfilter")
        command!!.setExecutor(Command())
    }
}
