package com.bitprofit.mono.bitprofit;

import android.os.AsyncTask;

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
	String dataParsed = "";
	String singleParsed = "";
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
				singleParsed = "Name: " + JO.get("name") +"\n" +
						"Symbol: " + JO.get("symbol") +"\n" +
						"Price: " + JO.get("price_usd") +"\n" +
						"Change: " + JO.get("percent_change_1h") +"\n";
				dataParsed += "\n"+singleParsed;
				String name = ""+JO.get("name");
				String symbol = ""+JO.get("symbol");
				String tempPrice = ""+JO.get("price_usd");
				double price = Double.parseDouble(tempPrice);
				Currency.addCurrency(name,symbol,price);
			}

		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(JSONException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		MainActivity.resetRecycleView();
	}
}
