package com.meteor.dailyreward.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.meteor.dailyreward.DailyReward;
import com.meteor.dailyreward.config.ConfigHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class Util {
	
	public static boolean check(String str, EntityPlayer player){
		int actualday = Check.getDay(Check.getPlayerStr(player)) + 1;
		int continuity = Check.getContinuity((Check.getPlayerStr(player))) + 1;
		if(str.startsWith("date")){
			String[] entry = str.split("==");
			String[] entries = entry[1].split(",");
			Time now = new Time();
			if(Integer.valueOf(entries[0]) == now.yyyy || Integer.valueOf(entries[0]) == -1)
				if(Integer.valueOf(entries[1]) == now.mm || Integer.valueOf(entries[1]) == -1)
					if(Integer.valueOf(entries[2]) == now.dd || Integer.valueOf(entries[2]) == -1)
						return true;
			
		}else{
			ScriptEngineManager manager = new ScriptEngineManager(null);
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			boolean result = false;
			try {
				result = (boolean) engine.eval(str);
			} catch (ScriptException e) {
				DailyReward.logger.info("error");
				e.printStackTrace();
			}
			return result;
			
		}
		return false;
	}
	
	public static void executeExact(EntityPlayer player, int actualday, int continuity){
		Time now = new Time();
		for(int i = 0; i < ConfigHandler.customreward.length; i++){
			String str = ConfigHandler.customreward[i].replace("%day", String.valueOf(actualday))
					.replace("%cont", String.valueOf(continuity))
					.replace("%posx", String.valueOf((int)player.posX))
					.replace("%posy", String.valueOf((int)player.posY))
					.replace("%posz", String.valueOf((int)player.posZ))
					.replace("%p", player.getGameProfile().getName())
					.replace("%y", String.valueOf(now.yyyy))
					.replace("%d", String.valueOf(now.dd))
					.replace("%m", String.valueOf(now.mm));
			Util.execute(str, player);
		}
	}
	
	public static void execute(String str, EntityPlayer player){
		String[] entries = str.split("#");
		if(check(entries[0], player) && entries.length >= 2)
			for(int i = 1; i < entries.length; i++){
				String[] entry = entries[i].split("==");
		
				if(entry[0].startsWith("stack")){
					ItemStack stack = getStackFromStr(entry[1]);
					if(player.world.isRemote)
						player.sendMessage(new TextComponentTranslation(I18n.format(stack.getItem().getUnlocalizedNameInefficiently(stack)+ ".name") + " x" + stack.getCount()));
					player.addItemStackToInventory(stack);
				}else if(entry[0].startsWith("command")){
					MinecraftServer world = player.getServer();
					world.getCommandManager().executeCommand(world, entry[1].replace("&p", player.getGameProfile().getName()));
				}else if(entry[0].startsWith("oredict")){
					
				}
			}		
	}
	
	public static ItemStack getStackFromStr(String str){
		return Reward.getReward(str);
	}

}
