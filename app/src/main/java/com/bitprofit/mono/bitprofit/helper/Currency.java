package com.bitprofit.mono.bitprofit.helper;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.main.async.FetchImage;

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
	int id;
	Drawable icon;
	public boolean needsReload,initialized;
	ImageView imageView;

	Currency(int id,String name, String symbol, double price, double total, double profit){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.total = total;
		this.profit = profit;
		this.id = id;
		needsReload = false;
		initialized = true;
	}

	public String getImageURL(){
		String temp = Var.toFormatName(name);
		return "https://files.coinmarketcap.com/static/img/coins/64x64/"+temp+".png";
	}

	public String getName(){
		return name;
	}

	public Drawable getIcon(){
		return icon;
	}

	public String getSymbol(){
		return symbol;
	}

	public String getId(){
		return ""+id;
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

	public static void updateCurrency(int id,String name, String symbol, double price, double total, double profit){
		Log.i("BitProfit","Updating "+name+" to: "+price);
		if(hashCurrencies.containsKey(""+id)){
			Currency c = hashCurrencies.get(""+id);
			c.name = name;
			c.symbol = symbol;
			c.price = price;
			c.total = total;
			c.profit = profit;
			c.id = id;
			FetchImage fImage = new FetchImage(c);
			fImage.execute();
			c.initialized = true;
		}else{
			addCurrency(id,name,symbol,price,total,profit);
		}

	}

	public static void addCurrency(int id,String name, String symbol, double price, double total, double profit){
		Currency c = new Currency(id,name,symbol,price,total, profit);
		c.initialized = true;
		FetchImage fImage = new FetchImage(c);
		fImage.execute();
		currencies.add(c);
		hashCurrencies.put(""+id,c);
	}

	public static void deleteCurrency(int id){
		Currency c = hashCurrencies.remove(""+id);
		currencies.remove(c);
	}

	public static void addUninitializedCurrency(int id,String name){
		Currency c = new Currency(id,name,"-",0,0,0);
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

	public static double getCurrencyTotalProfit(){
		double profit = 0;
		for(Currency c : currencies){
			profit += c.profit;
		}
		return profit;
	}
}
