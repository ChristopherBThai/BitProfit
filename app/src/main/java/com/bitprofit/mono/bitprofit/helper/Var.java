package com.bitprofit.mono.bitprofit.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Christopher Thai on 12/27/2017.
 */

public class Var{
	public static String INTENT_CURRENCY_LIST_NAME = "CurrencyListActivity.name";
	public static String INTENT_CURRENCY_INFO_ID= "CurrencyInfoActivity.name";

	public static boolean inUse = false;

	public static boolean dontUpdate = false;

	public static HashMap<String,Coin> hashCoins = new HashMap<>();
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public static ArrayList<AvailableCoin> availableCoins = new ArrayList<>();
	public static ArrayList<AvailableCoin> selectedCoins = new ArrayList<>();

	public static double total,totalProfit;

	public static final String FILENAME = "save.json";

	public static class Coin{
		public String name;
		public double coins,initial;
		public int id;
		Coin(int id,String name,double coins,double initial){
			this.name = name;
			this.coins = coins;
			this.initial = initial;
			this.id = id;
		}
	}

	public static Coin updateCoin(int id,String name,double coins,double initial){
		Coin c = hashCoins.get(""+id);
		c.name = name;
		c.coins = coins;
		c.initial = initial;
		return c;
	}

	public static Coin addNewCoin(String name,double coins,double initial){
		int id = Var.hashCoins.size();
		while(hashCoins.containsKey(""+id)){
			id--;
		}
		Coin c = new Coin(id,name,coins,initial);
		Var.coins.add(c);
		Var.hashCoins.put(""+id,c);
		return c;
	}

	public static Coin addCoin(int id,String name,double coins, double initial){
		if(hashCoins.containsKey(""+id)){
			Coin c = hashCoins.get(""+id);
			c.name = name;
			c.coins = coins;
			c.initial = initial;
			return c;
		}else{
			Coin c = new Coin(id,name,coins,initial);
			Var.coins.add(c);
			Var.hashCoins.put(""+id,c);
			return c;
		}
	}

	public static Coin addToCoin(int id,double initial, double amount){
		Coin c = hashCoins.get(""+id);
		c.initial += initial;
		c.coins += amount;
		return c;
	}

	public static Coin deleteCoin(int id){
		Coin c = hashCoins.remove(""+id);
		coins.remove(c);
		return c;
	}

	public static Coin getCoin(int id){
		return hashCoins.get(""+id);
	}

	public static void lock(){
		inUse = true;
	}

	public static void unlock(){
		inUse = false;
	}

	public static class AvailableCoin{
		public String name,abb;
		AvailableCoin(String name,String abb){
			this.name = name;
			this.abb = abb;
		}
		public boolean contains(String text){
			if(name.toLowerCase().contains(text.toLowerCase()))
				return true;
			if(abb.toLowerCase().contains(text.toLowerCase()))
				return true;
			return false;
		}
	}

	public static void addAvailableCoin(String name,String abb){
		Var.availableCoins.add(new AvailableCoin(name,abb));
	}
	public static String toDollars(double num){
		if(num<1&&num>-1)
			return String.format("$%,.5f",num);
		return String.format("$%,.2f",num);
	}

	public static String toBTC(double num){
		return String.format("%,.2f BTC",num);
	}

	public static String toFormatName(String name){
		name = name.toLowerCase();
		name = name.replaceAll("\\s*\\(.*\\)\\s*|\\s*\\[.*\\]\\s*","");
		name = name.replace(' ','-');
		return name;
	}

	public static void log(String log){
		Log.i("BitProfitLog",log);
	}

	public static void error(String error){
		Log.e("BitProfitLog",error);
	}
}
