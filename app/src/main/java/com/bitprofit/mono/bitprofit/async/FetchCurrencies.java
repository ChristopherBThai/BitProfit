package com.bitprofit.mono.bitprofit.async;

import android.os.AsyncTask;

import com.bitprofit.mono.bitprofit.MainActivity;
import com.bitprofit.mono.bitprofit.variables.Var;

/**
 * Reads list of available currencies
 * Created by Christopher Thai on 1/9/2018.
 */

public class FetchCurrencies extends AsyncTask<Void,Void,Void>{
	@Override
	protected Void doInBackground(Void... voids){
		Var.addAvailableCoin("Bitcoin","BTC");
		Var.addAvailableCoin("Litecoin","LTC");
		Var.addAvailableCoin("Ripple","XRP");
		Var.addAvailableCoin("Dogecoin","DOGE");
		Var.addAvailableCoin("Tron","TRON");
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		MainActivity.resetAvailableCurrencyRecycleView();
	}
}
