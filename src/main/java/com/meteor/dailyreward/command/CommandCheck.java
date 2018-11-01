package com.meteor.dailyreward.command;

import com.meteor.dailyreward.utils.Check;
import com.meteor.dailyreward.utils.Helper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandCheck extends CommandBase{

	@Override
	public String getName() {
		return "drcheck";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.drcheck.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 1){
            throw new WrongUsageException("commands.drcheck.usage");
        }
        else{
            EntityPlayerMP entityPlayerMP = args.length > 0 ? CommandBase.getPlayer(server, sender, args[0])
                    : CommandBase.getCommandSenderAsPlayer(sender);
            Helper.message(CommandBase.getCommandSenderAsPlayer(sender), Check.getDay(Check.getPlayerStr(entityPlayerMP)), Check.getContinuity(Check.getPlayerStr(entityPlayerMP)));
        }
	}
	
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }
}
