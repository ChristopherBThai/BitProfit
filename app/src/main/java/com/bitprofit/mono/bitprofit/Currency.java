package com.bitprofit.mono.bitprofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Thai on 12/20/2017.
 */

public class Currency{
	String name,symbol;
	double price;

	Currency(String name, String symbol, double price){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
	}

	private static List<Currency> currencies;

	public static void initializeData(){
		currencies = new ArrayList<>();
	}

	public static void addCurrency(String name, String symbol, double price){
		currencies.add(new Currency(name,symbol,price));
	}

	public static List<Currency> getCurrencies(){
		if(currencies==null)
			initializeData();
		return currencies;
	}
}
