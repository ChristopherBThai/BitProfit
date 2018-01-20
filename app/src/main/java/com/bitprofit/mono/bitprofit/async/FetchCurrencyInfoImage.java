package com.bitprofit.mono.bitprofit.async;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bitprofit.mono.bitprofit.variables.Var;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Christopher Thai on 1/20/2018.
 */

public class FetchCurrencyInfoImage extends AsyncTask<Void,Void,Void>{
	private String name;
	private Drawable d;
	private ImageView imageView;


	public FetchCurrencyInfoImage(String name,ImageView image){
		this.name = name;
		this.imageView = image;
	}

	@Override
	protected Void doInBackground(Void... voids){
		try{
			InputStream is = (InputStream) new URL(	"https://files.coinmarketcap.com/static/img/coins/64x64/"+name+".png").getContent();
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
}
