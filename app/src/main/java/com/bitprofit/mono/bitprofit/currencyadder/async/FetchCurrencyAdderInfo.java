package com.bitprofit.mono.bitprofit.currencyadder.async;

import android.os.AsyncTask;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyadder.CurrencyAdderActivity;
import com.bitprofit.mono.bitprofit.currencylist.CurrencyListActivity;
import com.bitprofit.mono.bitprofit.helper.Var;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Christopher Thai on 1/17/2018.
 */

public class FetchCurrencyAdderInfo extends AsyncTask<Void,Void,Void>{
	private String name,symbol,price;
	private CurrencyAdderActivity content;
	public FetchCurrencyAdderInfo(String formatname,CurrencyAdderActivity content){
		this.name = formatname;
		this.content = content;
	}

	@Override
	protected Void doInBackground(Void... voids){
		grabInfo();
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		content.setInfo(name,symbol,price);
	}

	public void grabInfo(){
		try{
			//Grabs info from api
			URL url = new URL("https://api.coinmarketcap.com/v1/ticker/"+name);
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

			//Display grabbed info
			JSONArray JA = new JSONArray(data);
			JSONObject JO = (JSONObject)JA.get(0);
			updateInfo(JO);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void updateInfo(JSONObject JO){
		try{
			name = JO.get("name").toString();
			Var.log("name "+name);
			symbol = JO.get("symbol").toString();
			Var.log("symbol "+symbol);
			price = JO.get("price_usd").toString();
			price = Var.toDollars(Double.parseDouble(price));
			Var.log("price "+price);
	}catch(Exception e){
			e.printStackTrace();
			Var.error("Failed grabbing info");
		}
	}

}
