package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.filter.ExplosionFilter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

class ExplosionEvents : Listener {
    @EventHandler
    fun onEntityExplode(e: EntityExplodeEvent) {
        ExplosionFilter.removeFilterBlock(e.blockList())
    }
}
