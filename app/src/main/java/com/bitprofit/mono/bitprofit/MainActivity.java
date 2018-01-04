package com.bitprofit.mono.bitprofit;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.bitprofit.mono.bitprofit.async.ReadJson;
import com.bitprofit.mono.bitprofit.async.WriteJson;
import com.bitprofit.mono.bitprofit.variables.Var;

import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity{

	public static TextView total, totalProfit;
	private static RecyclerView recycle;
	private static RVAdapter adapter;
	RelativeLayout adder;
	private static ViewGroup decor;

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
		Var.addCoin("raiblocks ",13.89999000 ,150);
		Var.addCoin("verify",39.966,50);
		Var.addCoin("bounty0x",239.7678921,50);
		Var.addCoin("cardano",50,10.9466612378);
		Var.addCoin("ripple",61.11809925,116.1084172342);
		Var.addCoin("dogecoin",2000,18.882434);
		Var.addCoin("reddcoin",1755.52958824,19.062553);


		adder = (RelativeLayout)findViewById(R.id.addcard);
		Button button = (Button)findViewById(R.id.add);
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				adder.setVisibility(View.VISIBLE);
			}
		});

		button = (Button)findViewById(R.id.search_back);
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				adder.setVisibility(View.INVISIBLE);
			}
		});

	}

	@Override
	protected void onResume(){
		super.onResume();
		Updater.start(60000);
	}

	@Override
	protected void onPause(){
		super.onPause();
		Updater.stop();
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

