package com.meteor.dailyreward.utils;

import java.util.ArrayList;
import java.util.List;

import com.meteor.dailyreward.config.ConfigHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class Helper {
	
	public static void message(EntityPlayer player, int day, int continuity){
		Time now = new Time();
		player.sendMessage(new TextComponentTranslation(ConfigHandler.notification, player.getGameProfile().getName(), day, continuity, now.yyyy, now.mm, now.dd).setStyle(new Style().setColor(TextFormatting.WHITE)));
	}
	
	public static void award(EntityPlayer player, List<ItemStack> stacks){
		if(stacks != null){
			for(int i = 0; i < stacks.size(); i++){
				player.sendMessage(new TextComponentTranslation(I18n.format(stacks.get(i).getItem().getUnlocalizedNameInefficiently(stacks.get(i))+ ".name") + " x" + stacks.get(i).getCount()));
				player.addItemStackToInventory(stacks.get(i));
			}
		}
	}
	
	public static List<ItemStack> getItemStacksWithCheck(String str, EntityPlayer player){
		String[] entry = str.replace(" ", "").split("\\|");
		for(int i = 0; i < entry.length; i++)
			if(Util.check(str, player))
				return getItemStacks(entry[i]);

		return null;
	}
	
	public static int getDay(String str){
		String[] entry = str.replace(" ", "").split("#");
		return Integer.valueOf(entry[0]);
	}
	
	public static List<ItemStack> getItemStacks(String str){
		String[] entry = str.replace(" ", "").split("#");
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for(int i = 1; i < entry.length; i++){
			if(entry[i].startsWith("stack"))
				stacks.add(Reward.getReward(entry[i]));
		}
		return stacks;
	}

}
