package commands

import BOT
import dev.minn.jda.ktx.interactions.commands.restrict
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.utils.FileUpload
import util.CommandUtil.addPlayerArgument
import util.CommandUtil.getPlayer
import util.ImageUtil
import java.io.File
import javax.imageio.ImageIO

class StatCommand: AbstractCommand() {
    override fun getName() = "stat"

    override fun getDescription() = "Shows a specified player's stats"

    override fun buildCommand(command: SlashCommandData) {
        command.restrict(true)
        command.addPlayerArgument()
    }

    override fun onCommand(event: GenericCommandInteractionEvent) {
        val username = event.getPlayer {
            event.reply("$it is not a valid username").queue()
        } ?: return
        val (embed, file) = BOT.db.getPlayerStats(username)
        val action = event.replyEmbeds(embed)
        file?.let { action.addFiles(it) }
        action.queue()
    }
}