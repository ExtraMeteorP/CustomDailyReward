package com.meteor.dailyreward.config;

import java.io.File;

import com.meteor.dailyreward.lib.LibMisc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static String[] customreward;
	public static boolean network = true;
	public static boolean message = true;
	public static String notification;
	
	public static Configuration config;
	
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		load();
	}
	
	public static void load() {
		String desc;
		
		config.addCustomCategoryComment("Custom Daily Reward", "");
		customreward = config.getStringList("dailyreward.customization", "general", new String[]{
				"%day==1#stack==minecraft:bread,0,3",
				"%day==2#stack==minecraft:planks,0,16",
				"%day==3#stack==minecraft:apple,0,5",
				"%day==4#stack==minecraft:coal,0,8",
				"%day==5#stack==minecraft:iron_sword",
				"%day==6#stack==minecraft:leather_helmet#stack==minecraft:leather_chestplate#stack==minecraft:leather_leggings#stack==minecraft:leather_boots",
				"%day==7#stack==minecraft:diamond"
		}, "");
		desc = "Set it to false to disable Network Detection.";
		network = loadPropBool("enable.network",desc,network);
		desc = "Set it to false to disable Notification Message.";
		message = loadPropBool("enable.message",desc,message);
		notification = config.getString("dailyreward.notification", "general", "Hello %s. You have logged in for %s days and %s days continuely. Today is %s.%s.%s ", "");
		
		if(config.hasChanged())
			config.save();
	}
	
	public static boolean loadPropBool(String propName, String desc, boolean default_) {
		Property prop = config.get(Configuration.CATEGORY_GENERAL, "dailyreward.config."+propName, default_);
		prop.setComment(desc);
		return prop.getBoolean(default_);
	}
	
	public static void loadPostInit() {
		if(config.hasChanged())
			config.save();
	}
	
	@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
	public static class ChangeListener {

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if(eventArgs.getModID().equals(LibMisc.MOD_ID))
				load();
		}

	}

}
