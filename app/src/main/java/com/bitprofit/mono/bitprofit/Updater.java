package com.bitprofit.mono.bitprofit;

import android.os.Handler;
import android.util.Log;

import com.bitprofit.mono.bitprofit.async.FetchData;

/**
 * Created by Christopher Thai on 12/28/2017.
 */

public class Updater{
	public static Handler handler = new Handler();
	public static int delay = 60000;

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
		FetchData process = new FetchData();
		process.execute();
		Log.i("BitProfit","Updating coins");
	}

}
