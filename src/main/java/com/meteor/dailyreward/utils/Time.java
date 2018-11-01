package com.meteor.dailyreward.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import com.meteor.dailyreward.DailyReward;
import com.meteor.dailyreward.config.ConfigHandler;

public class Time {
	
	public final int yyyy, mm, dd;
	
	public Time(){
		String[] entry = getWebsiteDatetime().trim().split("-");
		LocalDateTime now = LocalDateTime.now();
		if(ConfigHandler.network){
			this.yyyy = Integer.valueOf(entry[0]);
			this.mm = Integer.valueOf(entry[1]);
			this.dd = Integer.valueOf(entry[2]);
		}else{
			this.yyyy = now.getYear();
			this.mm = now.getMonthValue();
			this.dd = now.getDayOfMonth();
		}
	}
	
	public String getWebsiteDatetime(){
		try {
            String baiduUrl = "http://www.baidu.com";
            URL url = new URL(baiduUrl);
            URLConnection uc = url.openConnection();
            uc.connect();
            long ld = uc.getDate();
            Date date = new Date(ld);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
            DailyReward.logger.info(sdf.format(date));
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		LocalDateTime now = LocalDateTime.now();
        return now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
    }

}
