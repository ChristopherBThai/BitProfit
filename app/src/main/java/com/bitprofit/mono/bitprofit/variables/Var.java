package com.bitprofit.mono.bitprofit.variables;

import java.util.ArrayList;

/**
 * Created by Christopher Thai on 12/27/2017.
 */

public class Var{
	public static boolean inUse = false;
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
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

	public static void addCoin(String name,double coins,double initial){
		Var.coins.add(new Var.Coin(name,coins,initial));
	}

	public static void lock(){
		inUse = true;
	}

	public static void unlock(){
		inUse = false;
	}
}
