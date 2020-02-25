package com.meteor.dailyreward;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.meteor.dailyreward.command.CommandCheck;
import com.meteor.dailyreward.command.CommandExecute;
import com.meteor.dailyreward.config.ConfigHandler;
import com.meteor.dailyreward.lib.LibMisc;
import com.meteor.dailyreward.utils.Check;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = LibMisc.MOD_ID, 
	name = LibMisc.MOD_ID, 
	version = "1", 
	acceptableRemoteVersions = "*", 
	guiFactory = "com.meteor.dailyreward.config.ConfigGuiFactory")
public class DailyReward {
	
	@Instance(LibMisc.MOD_ID)
	public static DailyReward instance;
	
    public static final Logger logger = LogManager.getLogger(LibMisc.MOD_ID);
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event){
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
    }

    @EventHandler
	public void Init(FMLInitializationEvent event){
    	MinecraftForge.EVENT_BUS.register(new Check());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ConfigHandler.loadPostInit();
	}
	
	@EventHandler
    public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandCheck());
		event.registerServerCommand(new CommandExecute());
    }

}
