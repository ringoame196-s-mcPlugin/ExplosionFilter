package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.FilterGUIManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Command : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            return false
        }

        val subCommand = args[0]
        when (subCommand) {
            CommandConst.OPEN_COMMAND -> openCommand(sender)
        }

        return true
    }

    private fun openCommand(sender: CommandSender) {
        if (sender is Player) {
            FilterGUIManager.open(sender)
        } else {
            sender.sendMessage("${ChatColor.RED}プレイヤーのみ実行可能です")
        }
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(CommandConst.OPEN_COMMAND)
            else -> mutableListOf()
        }
    }
}
