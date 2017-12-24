package com.bitprofit.mono.bitprofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.bitprofit.mono.bitprofit.async.FetchData;

public class MainActivity extends AppCompatActivity{

	public static TextView name, price;
	private static RecyclerView recycle;
	private static RVAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		name = (TextView) findViewById(R.id.name);
		price = (TextView) findViewById(R.id.price);
		recycle = (RecyclerView) findViewById(R.id.rv);

		initRecyclerView();

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

	public static void resetRecycleView(){
		if(adapter != null)
			adapter.notifyDataSetChanged();
	}
}

