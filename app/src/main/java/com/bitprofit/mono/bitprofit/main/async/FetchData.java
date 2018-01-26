package com.bitprofit.mono.bitprofit.main.async;

import android.os.AsyncTask;

import com.bitprofit.mono.bitprofit.helper.Currency;
import com.bitprofit.mono.bitprofit.main.MainActivity;
import com.bitprofit.mono.bitprofit.helper.Var;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Fetches data from coinmarketcap api
 * Created by Christopher Thai on 12/20/2017.
 */

public class FetchData extends AsyncTask<Void,Void,Void>{

	private Var.Coin coin;

	public FetchData(Var.Coin coin){
		this.coin = coin;
	}

	@Override
	protected Void doInBackground(Void... voids){
		grabInfo(coin);
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		MainActivity.resetRecycleView();
		MainActivity.setTotalProfit();
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

			Currency.updateCurrency(coin.id,name,symbol,price,total,profit);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
