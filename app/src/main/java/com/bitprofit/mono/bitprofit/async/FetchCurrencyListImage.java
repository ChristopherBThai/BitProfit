package com.bitprofit.mono.bitprofit.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.Currency;

import java.io.InputStream;
import java.net.URL;

/**
 * Fetches image for currency adding list
 * Created by Christopher Thai on 1/12/2018.
 */

public class FetchCurrencyListImage extends AsyncTask<Void,Void,Void>{

	private String currencyName;
	private Drawable d;
	private ImageView imageView;


	public FetchCurrencyListImage(String name,ImageView image){
		name = name.toLowerCase();
		name = name.replaceAll("\\s*\\(.*\\)\\s*|\\s*\\[.*\\]\\s*","");
		currencyName = name.replace(' ','-');
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
