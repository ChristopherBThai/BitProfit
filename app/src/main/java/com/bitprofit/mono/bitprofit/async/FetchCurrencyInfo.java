package com.bitprofit.mono.bitprofit.async;

import android.os.AsyncTask;

/**
 * Created by Christopher Thai on 1/17/2018.
 */

public class FetchCurrencyInfo extends AsyncTask<Void,Void,Void>{
	String name;
	public FetchCurrencyInfo(String name){
		this.name = name;
	}

	@Override
	protected Void doInBackground(Void... voids){
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
	}

}
