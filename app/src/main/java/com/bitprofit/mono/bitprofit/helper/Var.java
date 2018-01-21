package com.bitprofit.mono.bitprofit.helper;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 12/27/2017.
 */

public class Var{
	public static String INTENT_CURRENCY_LIST_NAME = "CurrencyListActivity.name";
	public static String INTENT_CURRENCY_INFO_NAME= "CurrencyInfoActivity.name";

	public static boolean inUse = false;

	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public static ArrayList<AvailableCoin> availableCoins = new ArrayList<>();
	public static ArrayList<AvailableCoin> selectedCoins = new ArrayList<>();

	public static double total,totalProfit;

	public static final String FILENAME = "save.json";

	public static class Coin{
		public String name;
		public double coins,initial;
		Coin(String name,double coins,double initial){
			this.name = name;
			this.coins = coins;
			this.initial = initial;
		}
	}

	public static Coin addCoin(String name,double coins,double initial){
		Coin c = new Coin(name,coins,initial);
		Var.coins.add(c);
		return c;
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
