package com.bitprofit.mono.bitprofit.async;

import android.os.AsyncTask;

import com.bitprofit.mono.bitprofit.Currency;
import com.bitprofit.mono.bitprofit.MainActivity;
import com.bitprofit.mono.bitprofit.variables.Var;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Fetches data from coinmarketcap api
 * Created by Christopher Thai on 12/20/2017.
 */

public class FetchData extends AsyncTask<Void,Void,Void>{

	private double total=0,totalProfit=0,totalInitial=0;

	@Override
	protected Void doInBackground(Void... voids){
		while(Var.inUse);
		Var.lock();
		for(Var.Coin c : Var.coins){
			totalInitial += c.initial;
			grabInfo(c);
		}
		Var.unlock();
		totalProfit = total - totalInitial;
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		MainActivity.resetRecycleView();
		MainActivity.setTotalProfit(roundToTwoDecimal(total),roundToTwoDecimal(totalProfit));
	}

	private void grabInfo(Var.Coin coin){
		try{
			//Grabs info from api
			URL url = new URL("https://api.coinmarketcap.com/v1/ticker/"+coin.name);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			//Makes info into a string
			String line = "";
			String data = "";
			while(line != null){
				line = bufferedReader.readLine();
				data += line;
			}

			//Convert info to currency object
			JSONArray JA = new JSONArray(data);
			for(int i=0;i<JA.length();i++){
				JSONObject JO = (JSONObject)JA.get(i);
				addCurrency(JO,coin.coins,coin.initial);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void addCurrency(JSONObject JO,double coins,double initial){
		try{
			String name = ""+JO.get("name");
			String symbol = ""+JO.get("symbol");
			String tempPrice = ""+JO.get("price_usd");
			double price = Double.parseDouble(tempPrice);

			double total = price*coins;
			double profit = total - initial;
			this.total += total;

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