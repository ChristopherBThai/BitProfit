package com.bitprofit.mono.bitprofit.async;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.bitprofit.mono.bitprofit.MainActivity;
import com.bitprofit.mono.bitprofit.variables.Var;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Reads list of available currencies
 * Created by Christopher Thai on 1/9/2018.
 */

public class FetchCurrencies extends AsyncTask<Void,Void,Void>{

	@Override
	protected Void doInBackground(Void... voids){
		try{
			URL url = new URL("http://corg.network:8080/bitprofit/list");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while((line=bufferedReader.readLine())!=null){
				String[] words = line.split("-");
				Var.addAvailableCoin(words[0],words[1]);
			}

		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		MainActivity.resetAvailableCurrencyRecycleView();
	}

}
