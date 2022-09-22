package commands

import BOT
import dev.minn.jda.ktx.interactions.commands.restrict
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import util.CommandUtil
import util.CommandUtil.addServerArgument
import util.CommandUtil.getServer
import util.CommandUtil.isServerRole

class ClearTeamCommand: AbstractCommand() {
    override fun getName() = "clearteam"

    override fun getDescription() = "Clears all player from a team"

    override fun buildCommand(command: SlashCommandData) {
        command.restrict(true)
        command.addServerArgument()
    }

    override fun onCommand(event: GenericCommandInteractionEvent) {
        val (name, role) = event.getServer {
            event.reply("$it could not be found?!").queue()
            return
        }
        if (!CommandUtil.canMemberModifyTeam(event, role)) {
            return
        }

        BOT.db.clearTeam(name)
        event.reply("Successfully cleared $name").queue()
    }
}