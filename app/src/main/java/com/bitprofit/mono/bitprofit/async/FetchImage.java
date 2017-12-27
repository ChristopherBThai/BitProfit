package com.bitprofit.mono.bitprofit.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.Currency;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Christopher Thai on 12/24/2017.
 */

public class FetchImage extends AsyncTask<Void,Void,Void>{

	Currency currency;
	Drawable d;

	public FetchImage(Currency c){
		currency = c;
	}

	@Override
	protected Void doInBackground(Void... voids){
		try{
			InputStream is = (InputStream) new URL(currency.getImageURL()).getContent();
			d = Drawable.createFromStream(is, null);
			currency.setIcon(d);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		if(currency.needsReload){
			currency.reloadImage(d);
		}
	}
}