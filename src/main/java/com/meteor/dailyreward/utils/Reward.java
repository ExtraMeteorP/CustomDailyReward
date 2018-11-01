package com.meteor.dailyreward.utils;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class Reward {
	
    @Nullable
    public final Object obj;
    public final int min, max;

    public Reward(@Nullable Object obj, int min, int max) {
        this.obj = obj;
        this.min = min;
        this.max = max;
    }
    
    public Reward(@Nullable Object obj, int amount){
    	this(obj, amount, amount);
    }
    
    public Reward(@Nullable Object obj){
    	this(obj, 1);
    }
    
    public ItemStack getReward(Reward reward){
    	Object obj = reward.obj;
    	Random rand = new Random();
    	int stacksize = MathHelper.getInt(rand, reward.min, reward.max);
    	if(obj instanceof Block)
    		return new ItemStack((Block) obj, stacksize);
    	if(obj instanceof Item)
    		return new ItemStack((Block) obj, stacksize);
    	if(obj instanceof ItemStack)
    		return (ItemStack) obj;
    	if(obj instanceof String){
    		return getReward((String) obj);
    	}
    	return ItemStack.EMPTY;
    }
    
    public static ItemStack getReward(String str){
    	Random rand = new Random();
		String[] entry = str.replace(" ", "").split(","); 	
		int meta = entry.length > 1 ? Integer.valueOf(entry[1]) : 0;
		ItemStack stack = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(entry[0])), 1, meta);
		if(entry.length <= 2)
			return stack;
		switch(entry.length){
    		case 3:
    			stack.setCount(Integer.valueOf(entry[2]));
    			return stack;
    		case 4:
    			stack.setCount(Integer.valueOf(MathHelper.getInt(rand, Integer.valueOf(entry[2]), Integer.valueOf(entry[3]))));
    			return stack;		
		}
		if(entry.length > 4)
			if (entry.length != 3)
				throw new IllegalArgumentException("Illegal entry found when reading Daily Reward config file");
		return ItemStack.EMPTY;
    }

}
