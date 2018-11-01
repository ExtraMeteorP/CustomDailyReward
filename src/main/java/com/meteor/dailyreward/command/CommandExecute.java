package com.meteor.dailyreward.command;

import com.meteor.dailyreward.utils.Time;
import com.meteor.dailyreward.utils.Util;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandExecute extends CommandBase{

	@Override
	public String getName() {
		return "drexecute";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.execute.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 2){
            throw new WrongUsageException("commands.drcheck.usage");
        }
        else{
           int day = Integer.valueOf(args[0]);
           int cont = Integer.valueOf(args[1]);
           Util.executeExact(CommandBase.getCommandSenderAsPlayer(sender), day, cont);
        }
	}
	
	public static String makeStr(int day, int cont){
		Time now = new Time();
		return day+"#test#"+now.yyyy+","+now.mm+","+now.dd+"#"+cont;
	}
	
    @Override
    public int getRequiredPermissionLevel(){
        return 4;
    }
}
