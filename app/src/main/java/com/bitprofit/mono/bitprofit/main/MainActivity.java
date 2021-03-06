package com.bitprofit.mono.bitprofit.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyinfo.CurrencyInfoActivity;
import com.bitprofit.mono.bitprofit.currencylist.CurrencyListActivity;
import com.bitprofit.mono.bitprofit.currencylist.async.FetchCurrencyList;
import com.bitprofit.mono.bitprofit.main.async.FetchData;
import com.bitprofit.mono.bitprofit.helper.ReadJson;
import com.bitprofit.mono.bitprofit.helper.WriteJson;
import com.bitprofit.mono.bitprofit.currencylist.RVACurrencyList;
import com.bitprofit.mono.bitprofit.helper.Currency;
import com.bitprofit.mono.bitprofit.helper.Var;

public class MainActivity extends AppCompatActivity{

	public static TextView total, totalProfit;
	public static int pos,neg,zero;
	private static RecyclerView recycle;
	private static RVAMain adapter;
	RelativeLayout info;

	private static boolean needsUpdate = true;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		//Removes status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		Var.log("ONCREATE MAIN");

		total = (TextView) findViewById(R.id.bartotal);
		totalProfit = (TextView) findViewById(R.id.barprofit);
		recycle = (RecyclerView) findViewById(R.id.rv);
		pos = getResources().getColor(R.color.positive_font);
		neg = getResources().getColor(R.color.negative_font);
		zero = getResources().getColor(R.color.font);


		initRecyclerView();
/*
		Var.addNewCoin("bitcoin",.01018814,150);
		Var.addNewCoin("ethereum",.21102305,100.12);
		Var.addNewCoin("litecoin",1.55290242,400);
		Var.addNewCoin("raiblocks ",13.89999000 ,150);
		Var.addNewCoin("verify",39.966,50);
		Var.addNewCoin("bounty0x",239.7678921,50);
		Var.addNewCoin("cardano",50,10.9466612378);
		Var.addNewCoin("ripple",199.46379946,316.1084172342);
		Var.addNewCoin("dragonchain",18.934047,50);
		Var.addNewCoin("tron",308.691,50);
		Var.addNewCoin("dogecoin",2000,18.882434);
		Var.addNewCoin("reddcoin",1755.52958824,19.062553);
		Var.addNewCoin("siacoin",733.8031537,50);
		Var.addNewCoin("nem",36.92100000,50);
*/
		load();
		initButtons();

	}

	@Override
	protected void onResume(){
		super.onResume();
		resetRecycleView();
		setTotalProfit();
		if(!Var.dontUpdate)
			Updater.start(60000);
		Var.dontUpdate = false;
	}

	@Override
	protected void onPause(){
		super.onPause();
		Updater.stop();
	}


	protected void initRecyclerView(){
		LinearLayoutManager llm = new LinearLayoutManager(this);
		adapter = new RVAMain(Currency.getCurrencies(),(RelativeLayout)findViewById(R.id.infocard),this);

		recycle.setHasFixedSize(true);
		recycle.setLayoutManager(llm);
		recycle.setAdapter(adapter);

		FetchCurrencyList fetch = new FetchCurrencyList();
		fetch.execute();
	}

	protected void initButtons(){
		Button button = (Button)findViewById(R.id.add);
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//Display currency_list
				Var.dontUpdate = true;
				Intent intent = new Intent(MainActivity.this, CurrencyListActivity.class);
				startActivity(intent);
			}
		});
	}

	public void save(){
		try{
			WriteJson write = new WriteJson(openFileOutput(Var.FILENAME, Context.MODE_PRIVATE));
			write.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void load(){
		try{
			ReadJson read = new ReadJson(openFileInput(Var.FILENAME));
			read.execute();
		}catch(Exception e){

		}
	}

	public static void resetRecycleView(){
		if(adapter != null)
			adapter.notifyDataSetChanged();
	}

	public static void setTotalProfit(){
		String total = Currency.getCurrencyTotal();
		double totalProfit = Currency.getCurrencyTotalProfit();
		MainActivity.total.setText(total);
		MainActivity.totalProfit.setText(Var.toDollars(totalProfit));
		if(totalProfit>0)
			MainActivity.totalProfit.setTextColor(pos);
		else if(totalProfit<0)
			MainActivity.totalProfit.setTextColor(neg);
		else
			MainActivity.totalProfit.setTextColor(zero);
	}

	public void showInfo(int id,String holding,String profit,int color){
		Var.dontUpdate = true;
		Intent intent = new Intent(this, CurrencyInfoActivity.class);
		intent.putExtra(Var.INTENT_CURRENCY_INFO_ID,""+id);
		intent.putExtra(Var.INTENT_CURRENCY_INFO_HOLDING,holding);
		intent.putExtra(Var.INTENT_CURRENCY_INFO_PROFIT,profit);
		intent.putExtra(Var.INTENT_CURRENCY_INFO_COLOR,""+color);
		startActivity(intent);
	}
}

