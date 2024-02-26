package com.github.sigureruri.yuquest.playerdata.bukkit

import com.github.sigureruri.yuquest.playerdata.PlayerDataOperator
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent
import java.util.logging.Logger
import kotlin.math.log

class PlayerDataListener(private val operator: PlayerDataOperator, private val logger: Logger) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val uuid = player.uniqueId

        try {
            if (operator.canLoad(uuid)) {
                if (operator.existsInLocalRepository(uuid)) {
                    logger.warning("Playerdata of $uuid has already been created")
                } else {
                    operator.load(uuid)
                }
            } else {
                operator.createNew(uuid)
            }
        } catch (e: Throwable) {
            logger.warning("Failed to load playerdata of $uuid")
            e.printStackTrace()

            player.kick(generateErrorMsgCausedByLoadingProcess(player))
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId

        try {
            operator.save(uuid)
            operator.removeFromLocalRepository(uuid)
        } catch (e: Throwable) {
            logger.warning("Failed to save playerdata of $uuid")
            e.printStackTrace()
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onYuQuestEnable(event: PluginEnableEvent) {
        event.plugin.server.onlinePlayers.forEach { player ->
            val uuid = player.uniqueId

            try {
                if (operator.existsInLocalRepository(uuid)) {
                    logger.warning("Playerdata of $uuid has already been created")
                } else {
                    operator.load(uuid)
                }
            } catch (e: Throwable) {
                logger.warning("Failed to load playerdata of $uuid")
                e.printStackTrace()

                player.kick(generateErrorMsgCausedByLoadingProcess(player))
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onYuQuestDisable(event: PluginDisableEvent) {
        operator.getAllFromLocalRepository()
            .map { it.uuid }
            .forEach { uuid ->
                try {
                    operator.save(uuid)
                    operator.removeFromLocalRepository(uuid)
                } catch (e: Throwable) {
                    logger.warning("Failed to save playerdata of $uuid")
                    e.printStackTrace()
                }
            }
    }

    private fun generateErrorMsgCausedByLoadingProcess(player: Player): Component {
        return Component.text("[YuQuest] An error was occurred while loading your player data.").color(NamedTextColor.RED)
            .append(Component.newline())
            .append(Component.text("Please inform the server administrator with the following information:").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
            .append(Component.newline())
            .append(Component.text("uuid: ${player.uniqueId}, id: ${player.name}, first_join: ${player.hasPlayedBefore()}"))
    }
}