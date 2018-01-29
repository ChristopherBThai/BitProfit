package com.bitprofit.mono.bitprofit.currencyinfo.async;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.helper.Var;
import com.bitprofit.mono.bitprofit.main.MainActivity;

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

public class FetchCurrencyInfo extends AsyncTask<Void,Void,Void>{
	String name,symbol,price,change_1h,change_24h,change_7d,volume,market_cap,available_supply,total_supply,max_supply;
	int color_1h,color_24h,color_7d;
	AppCompatActivity layout;
	public FetchCurrencyInfo(String name,AppCompatActivity layout){
		this.name = name;
		this.layout = layout;
	}

	@Override
	protected Void doInBackground(Void... voids){
		Var.log("Grabbing info");
		grabInfo();
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		((TextView)layout.findViewById(R.id.info_name)).setText(name);
		((TextView)layout.findViewById(R.id.info_abb)).setText(symbol);
		((TextView)layout.findViewById(R.id.info_price)).setText(price);

		((TextView)layout.findViewById(R.id.info_change_1h)).setText(change_1h);
		((TextView)layout.findViewById(R.id.info_change_24h)).setText(change_24h);
		((TextView)layout.findViewById(R.id.info_change_7d)).setText(change_7d);
		((TextView)layout.findViewById(R.id.info_change_1h)).setTextColor(color_1h);
		((TextView)layout.findViewById(R.id.info_change_24h)).setTextColor(color_24h);
		((TextView)layout.findViewById(R.id.info_change_7d)).setTextColor(color_7d);

		((TextView)layout.findViewById(R.id.info_volume)).setText(volume);
		((TextView)layout.findViewById(R.id.info_cap)).setText(market_cap);

		((TextView)layout.findViewById(R.id.info_available_supply)).setText(available_supply);
		((TextView)layout.findViewById(R.id.info_total_supply)).setText(total_supply);
		((TextView)layout.findViewById(R.id.info_max_supply)).setText(max_supply);
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
			String name = JO.get("name").toString();
			Var.log("name "+name);
			this.name = name;
			String symbol = JO.get("symbol").toString();
			Var.log("symbol "+symbol);
			this.symbol = symbol;
			double price = Double.parseDouble(JO.get("price_usd").toString());
			Var.log("price " + price);
			this.price = Var.toDollars(price);

			String change_1h = JO.get("percent_change_1h").toString();
			Var.log("1h "+change_1h);
			this.change_1h = change_1h+"%";
			double temp = Double.parseDouble(change_1h);
			if(temp>0)
				color_1h = MainActivity.pos;
			else if(temp<0)
				color_1h = MainActivity.neg;
			else
				color_1h = MainActivity.zero;
			String change_24h = JO.get("percent_change_24h").toString();
			Var.log("24h "+change_24h);
			this.change_24h = change_24h+"%";
			temp = Double.parseDouble(change_24h);
			if(temp>0)
				color_24h = MainActivity.pos;
			else if(temp<0)
				color_24h = MainActivity.neg;
			else
				color_24h = MainActivity.zero;
			String change_7d= JO.get("percent_change_7d").toString();
			Var.log("7d"+change_7d);
			this.change_7d = change_7d+"%";
			temp = Double.parseDouble(change_7d);
			if(temp>0)
				color_7d= MainActivity.pos;
			else if(temp<0)
				color_7d= MainActivity.neg;
			else
				color_7d= MainActivity.zero;

			double volume = Double.parseDouble(JO.get("24h_volume_usd").toString());
			Var.log("volume "+volume);
			this.volume = Var.toDollars(volume);
			double market_cap = Double.parseDouble(JO.get("market_cap_usd").toString());
			Var.log("cap "+market_cap);
			this.market_cap = Var.toDollars(market_cap);

			double available_supply = Double.parseDouble(JO.get("available_supply").toString());
			Var.log("available "+available_supply);
			this.available_supply = Var.toBTC(available_supply);
			double total_supply = Double.parseDouble(JO.get("total_supply").toString());
			Var.log("total "+total_supply);
			this.total_supply = Var.toBTC(total_supply);
			double max_supply = Double.parseDouble(JO.get("max_supply").toString());
			Var.log("max "+max_supply);
			this.max_supply = Var.toBTC(max_supply);
		}catch(Exception e){
			e.printStackTrace();
			Var.error("Failed grabbing info");
		}
	}

}
