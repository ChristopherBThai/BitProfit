package com.bitprofit.mono.bitprofit.currencyadder.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.helper.Var;

import java.io.InputStream;
import java.net.URL;

/**
 * Fetches image for currency adding list
 * Created by Christopher Thai on 1/12/2018.
 */

public class FetchCurrencyAdderImage extends AsyncTask<Void,Void,Void>{

	private String currencyName;
	private Drawable d;
	private ImageView imageView;


	public FetchCurrencyAdderImage(String formatname, ImageView image){
		currencyName = formatname;
		this.imageView = image;
	}

	@Override
	protected Void doInBackground(Void... voids){
		try{
			InputStream is = (InputStream) new URL(	"https://files.coinmarketcap.com/static/img/coins/64x64/"+currencyName+".png").getContent();
			d = Drawable.createFromStream(is, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
		if(d!=null)
			imageView.setImageDrawable(d);

	}

	public void set(String name, ImageView image){
		this.currencyName = name;
		this.imageView = image;
	}
}
