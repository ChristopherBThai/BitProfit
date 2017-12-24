package com.bitprofit.mono.bitprofit;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.async.FetchImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Thai on 12/20/2017.
 */

public class Currency{
	String name,symbol;
	double price,total,profit;
	Drawable icon;
	public boolean needsReload;
	ImageView imageView;

	Currency(String name, String symbol, double price, double total, double profit){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.total = total;
		this.profit = profit;
		needsReload = false;
	}

	public String getImageURL(){
		String temp = name;
		temp = temp.toLowerCase();
		temp = temp.replace(' ','-');
		return "https://files.coinmarketcap.com/static/img/coins/64x64/"+temp+".png";
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

	private static List<Currency> currencies;

	public static void initializeData(){
		currencies = new ArrayList<>();
	}

	public static void addCurrency(String name, String symbol, double price, double total, double profit){
		Currency c = new Currency(name,symbol,price,total, profit);
		FetchImage fImage = new FetchImage(c);
		fImage.execute();
		currencies.add(c);
	}

	public static List<Currency> getCurrencies(){
		if(currencies==null)
			initializeData();
		return currencies;
	}
}
