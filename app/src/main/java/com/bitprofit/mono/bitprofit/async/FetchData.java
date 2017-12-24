package com.bitprofit.mono.bitprofit.async;

import android.os.AsyncTask;

import com.bitprofit.mono.bitprofit.Currency;
import com.bitprofit.mono.bitprofit.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Christopher Thai on 12/20/2017.
 */

public class FetchData extends AsyncTask<Void,Void,Void>{

	String data = "";
	ArrayList<Currency> currencies;
	@Override
	protected Void doInBackground(Void... voids){
		try{
			URL url = new URL("https://api.coinmarketcap.com/v1/ticker/");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line = "";
			while(line != null){
				line = bufferedReader.readLine();
				data += line;
			}

			currencies = new ArrayList<Currency>();
			JSONArray JA = new JSONArray(data);
			for(int i=0;i<JA.length();i++){
				JSONObject JO = (JSONObject)JA.get(i);
				addCurrency(JO);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		MainActivity.resetRecycleView();
	}

	private void addCurrency(JSONObject JO){
		try{
			String name = ""+JO.get("name");
			String symbol = ""+JO.get("symbol");
			String tempPrice = ""+JO.get("price_usd");
			double price = Double.parseDouble(tempPrice);

			double initial = 400;
			double coins = .01;

			double total = price*coins;
			double profit = total - initial;

			price = roundToTwoDecimal(price);
			total = roundToTwoDecimal(total);
			profit = roundToTwoDecimal(profit);

			Currency.addCurrency(name,symbol,price,total,profit);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private double roundToTwoDecimal(double num){
		num *= 100;
		num = Math.round(num);
		num /= 100;
		return num;
	}
}
