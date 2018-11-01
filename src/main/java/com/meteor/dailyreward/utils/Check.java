package com.meteor.dailyreward.utils;

import java.time.LocalDate;

import com.meteor.dailyreward.DailyReward;
import com.meteor.dailyreward.config.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class Check {
	
	//day#username#YYYY,MM,DD#cont
	@SubscribeEvent
	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		Time now = new Time();
		//reward
		if(compare(getPlayerStr(event.player), now.yyyy, now.mm, now.dd)){
			int actualday = getDay(getPlayerStr(event.player)) + 1;
			int continuity = getContinuity(getPlayerStr(event.player));
			if(checkContinuity(getPlayerStr(event.player), now.yyyy, now.mm, now.dd))
				continuity+=1;
			DailyReward.logger.info(getPlayerStr(event.player));
			
			if(ConfigHandler.message)
				Helper.message(event.player, actualday, continuity);
			
			Util.executeExact(event.player, actualday, continuity);
			setPlayerStr(event.player, actualday, continuity);
		}
	}
	
    public static NBTTagCompound getPlayerPersistentNBT(EntityPlayer player) {
        NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
        if (!nbt.hasKey("dailyreward_data")) {
            nbt.setTag("dailyreward_data", new NBTTagCompound());
            ((NBTTagCompound) nbt.getTag("dailyreward_data")).setString("recordkey", "0#" + player.getGameProfile().getName() + "#0000,00,00#0");
            player.getEntityData().setTag("PlayerPersisted", nbt);
        }
        return nbt;
    }
	
	public static String getPlayerStr(EntityPlayer player){
		NBTTagCompound nbt = getPlayerPersistentNBT(player);
		if (nbt.hasKey("dailyreward_data")) {
            return ((NBTTagCompound) nbt.getTag("dailyreward_data")).getString("recordkey");
        }
		return "null";
	}
	
	public void setPlayerStr(EntityPlayer player, int day, int continuity){
		Time now = new Time();
		setPlayerStr(player, day + "#" + player.getGameProfile().getName() + "#" + now.yyyy + "," + now.mm + "," + now.dd + "#" + continuity);
	}
	
	public void setPlayerStr(EntityPlayer player, String str){
		NBTTagCompound nbt = getPlayerPersistentNBT(player);
		if (nbt.hasKey("dailyreward_data")) {
			((NBTTagCompound) nbt.getTag("dailyreward_data")).setString("recordkey", str);
        }
	}
	
	public static int getDay(String str){
		return Helper.getDay(str);
	}
	
	public boolean checkContinuity(String str, int year, int month, int day){
		int y = getYYYY(getYYYYMMDD(str));
		int m = getMM(getYYYYMMDD(str));
		int d = getDD(getYYYYMMDD(str));
		if(y == 0 || m == 0 || d == 0)
			return true;
		LocalDate l1 = LocalDate.parse(y+"-"+String.format("%02d",m)+"-"+String.format("%02d",d));
		LocalDate l2 = LocalDate.parse(year+"-"+String.format("%02d",month)+"-"+String.format("%02d",day));
		return l1.plusDays(1).isEqual(l2);
	}
	
	public boolean compare(String str, int year, int month, int day){
		int y = getYYYY(getYYYYMMDD(str));
		int m = getMM(getYYYYMMDD(str));
		int d = getDD(getYYYYMMDD(str));
		if(year > y)
			return true;
		if(year == y && month > m)
			return true;
		if(year == y && month == m && day > d)
			return true;
		return false;
	}
	
	public static int getContinuity(String str){
		String[] entry = str.replace(" ", "").split("#");
		return Integer.valueOf(entry[3]);
	}
	
	public static String getYYYYMMDD(String str){
		String[] entry = str.replace(" ", "").split("#");
		return entry[2];
	}
	
	public static int getYYYY(String str){
		String[] entry = str.replace(" ", "").split(",");
		return Integer.valueOf(entry[0]);
	}
	
	public static int getMM(String str){
		String[] entry = str.replace(" ", "").split(",");
		return Integer.valueOf(entry[1]);
	}
	
	public static int getDD(String str){
		String[] entry = str.replace(" ", "").split(",");
		return Integer.valueOf(entry[2]);
	}
	
	public static String getPlayerName(String str){
		String[] entry = str.replace(" ", "").split("#");
		return entry[1];
	}

}
