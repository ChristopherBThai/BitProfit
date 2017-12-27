package com.bitprofit.mono.bitprofit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.bitprofit.mono.bitprofit.async.FetchData;
import com.bitprofit.mono.bitprofit.async.WriteJson;
import com.bitprofit.mono.bitprofit.variables.Var;

public class MainActivity extends AppCompatActivity{

	public static final String FILENAME = "save.json";

	public static TextView total, totalProfit;
	private static RecyclerView recycle;
	private static RVAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		removeStatusBar();
		setContentView(R.layout.activity_main);

		total = (TextView) findViewById(R.id.bartotal);
		totalProfit = (TextView) findViewById(R.id.barprofit);
		recycle = (RecyclerView) findViewById(R.id.rv);

		initRecyclerView();

		Var.addCoin("bitcoin",.01018814,150);
		Var.addCoin("ethereum",.21102305,100.12);
		Var.addCoin("litecoin",1.55290242,400);
		Var.addCoin("cardano",50,10.9466612378);
		Var.addCoin("ripple",20,16.1084172342);
		Var.addCoin("dogecoin",2000,18.882434);
		Var.addCoin("reddcoin",1755.52958824,19.062553);

		FetchData process = new FetchData();
		process.execute();

	}

	protected void initRecyclerView(){
		LinearLayoutManager llm = new LinearLayoutManager(this);
		adapter = new RVAdapter(Currency.getCurrencies());

		recycle.setHasFixedSize(true);
		recycle.setLayoutManager(llm);
		recycle.setAdapter(adapter);
	}

	public void save(){
		try{
			WriteJson write = new WriteJson(openFileOutput(FILENAME, Context.MODE_PRIVATE));
			write.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void resetRecycleView(){
		if(adapter != null)
			adapter.notifyDataSetChanged();
	}

	public void removeStatusBar(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	public static void setTotalProfit(double total,double totalProfit){
		MainActivity.total.setText("$"+total);
		MainActivity.totalProfit.setText("$"+totalProfit);
	}
}

