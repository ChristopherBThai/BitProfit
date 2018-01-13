package com.bitprofit.mono.bitprofit;

import android.os.Handler;
import android.util.Log;

import com.bitprofit.mono.bitprofit.async.FetchData;
import com.bitprofit.mono.bitprofit.variables.Var;

/**
 * Created by Christopher Thai on 12/28/2017.
 */

public class Updater{
	public static Handler handler = new Handler();
	public static int delay = 60000;

	static boolean tempCheck = false;

	public static void start(int ms){
		delay = ms;
		fetch();
		handler.postDelayed(new Runnable(){
			public void run(){
				//run every ms
				fetch();
				handler.postDelayed(this, delay);
			}
		},delay);
	}

	public static void stop(){
		handler.removeCallbacksAndMessages(null);
	}

	public static void restart(){
		stop();
		start(delay);
	}

	private static void fetch(){
		if(!tempCheck){
			for(Var.Coin c:Var.coins){
				Currency.addUninitializedCurrency(c.name);
			}
			tempCheck = true;
		}
		for(Var.Coin c : Var.coins){
			Log.i("BitProfit","Updating coin "+c.name);
			FetchData process = new FetchData(c);
			process.execute();
		}
	}

}
