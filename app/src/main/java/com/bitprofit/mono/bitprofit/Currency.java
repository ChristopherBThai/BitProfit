package com.bitprofit.mono.bitprofit;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.async.FetchImage;
import com.bitprofit.mono.bitprofit.variables.Var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Variables to display onto the screen
 * Created by Christopher Thai on 12/20/2017.
 */

public class Currency{
	String name,symbol;
	double price,total,profit;
	Drawable icon;
	public boolean needsReload,initialized;
	ImageView imageView;

	Currency(String name, String symbol, double price, double total, double profit){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.total = total;
		this.profit = profit;
		needsReload = false;
		initialized = true;
	}

	public String getImageURL(){
		String temp = name;
		temp = temp.toLowerCase();
		temp = temp.replace(' ','-');
		return "https://files.coinmarketcap.com/static/img/coins/64x64/"+temp+".png";
	}

	public String getTotal(){
		if(!initialized)
			return "-";
		return Var.toDollars(total);
	}

	public String getProfit(){
		if(!initialized)
			return "-";
		return Var.toDollars(profit);
	}

	public String getPrice(){
		if(!initialized)
			return "-";
		return Var.toDollars(price);
	}

	public void setIcon(Drawable icon){
		this.icon = icon;
	}

	public void needsReload(ImageView image){
		this.imageView = image;
		this.needsReload = true;
	}

	public void reloadImage(Drawable d){
		this.imageView.setImageDrawable(d);
		this.needsReload = false;
	}

	private static ArrayList<Currency> currencies;
	private static HashMap<String,Currency> hashCurrencies;

	public static void initializeData(){
		currencies = new ArrayList<>();
		hashCurrencies = new HashMap<String,Currency>();
	}

	public static void updateCurrency(String hash,String name, String symbol, double price, double total, double profit){
		Log.i("BitProfit","Updating "+name+" to: "+price);
		if(hashCurrencies.containsKey(hash)){
			Currency c = hashCurrencies.get(hash);
			c.name = name;
			c.symbol = symbol;
			c.price = price;
			c.total = total;
			c.profit = profit;
			FetchImage fImage = new FetchImage(c);
			fImage.execute();
			c.initialized = true;
		}else{
			addCurrency(hash,name,symbol,price,total,profit);
		}

	}

	public static void addCurrency(String hash,String name, String symbol, double price, double total, double profit){
		Currency c = new Currency(name,symbol,price,total, profit);
		c.initialized = true;
		FetchImage fImage = new FetchImage(c);
		fImage.execute();
		currencies.add(c);
		hashCurrencies.put(hash,c);
	}

	public static void addUninitializedCurrency(String name){
		Currency c = new Currency(name,"-",0,0,0);
		c.initialized = false;
		currencies.add(c);
		hashCurrencies.put(name,c);
	}


	public static List<Currency> getCurrencies(){
		if(currencies==null)
			initializeData();
		return currencies;
	}

	public static String getCurrencyTotal(){
		double total = 0;
		for(Currency c : currencies){
			total += c.total;
		}
		return Var.toDollars(total);
	}

	public static String getCurrencyTotalProfit(){
		double profit = 0;
		for(Currency c : currencies){
			profit += c.profit;
		}
		return Var.toDollars(profit);
	}
}
