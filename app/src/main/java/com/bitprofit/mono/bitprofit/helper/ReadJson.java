package com.bitprofit.mono.bitprofit.helper;

import android.os.AsyncTask;

import com.bitprofit.mono.bitprofit.main.Updater;
import com.bitprofit.mono.bitprofit.helper.Var;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Grabs the user's info
 * Created by Christopher Thai on 12/26/2017.
 */

public class ReadJson extends AsyncTask<Void,Void,Void>{

	public FileInputStream stream;

	public ReadJson(FileInputStream stream){
		this.stream = stream;
	}

	@Override
	protected Void doInBackground(Void... voids){
		Var.log("Loading Cryptos");
		try{
			if(stream.available()==0){
				return null;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			String data = "";
			while(line != null){
				line = reader.readLine();
				data += line;
			}

			//Convert info to currency object
			JSONArray JA = new JSONArray(data);
			for(int i=0;i<JA.length();i++){
				JSONObject JO = (JSONObject)JA.get(i);
				addCoin(JO);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		Updater.restart();
	}

	private void addCoin(JSONObject coin){
		try{
			Var.Coin c = Var.addCoin(Integer.parseInt(coin.get("id").toString()),
					coin.get("name").toString(),
					Double.parseDouble(coin.get("coins").toString()),
					Double.parseDouble(coin.get("initial").toString()));
			Var.log("Read from save: "+c.name);
		}catch(Exception e){
			e.printStackTrace();
			Var.error("Failed reading from save");
		}
	}
}
