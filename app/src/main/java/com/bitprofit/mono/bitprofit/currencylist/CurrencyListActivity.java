package com.bitprofit.mono.bitprofit.currencylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyadder.CurrencyAdderActivity;
import com.bitprofit.mono.bitprofit.helper.Var;

public class CurrencyListActivity extends AppCompatActivity{


	private static RVACurrencyList rvaList;
	private static RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.currency_list);
		initButtons();
		initRecycler();
		initSearchView();
	}

	public void selectCurrency(String name){
		//Go to next layout
		Intent intent = new Intent(this, CurrencyAdderActivity.class);
		intent.putExtra(Var.INTENT_CURRENCY_LIST_NAME,Var.toFormatName(name));
		startActivity(intent);
	}

	private void initButtons(){
		((Button)findViewById(R.id.search_back)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//Go back to main screen
				CurrencyListActivity.this.finish();
			}
		});
	}

	private void initRecycler(){
		LinearLayoutManager llm = new LinearLayoutManager(this);
		rvaList = new RVACurrencyList(Var.availableCoins,this);
		recyclerView = (RecyclerView)findViewById(R.id.search_list);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(llm);
		recyclerView.setAdapter(rvaList);
	}

	private void initSearchView(){
		SearchView searchView = (SearchView)findViewById(R.id.search_search);
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
			@Override
			public boolean onQueryTextSubmit(String query){
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText){
				if(newText.equals("")){
					rvaList.setCurrencies(Var.availableCoins);
				}else{
					Var.selectedCoins.clear();
					rvaList.setCurrencies(Var.selectedCoins);
					for(Var.AvailableCoin currency : Var.availableCoins){
						if(currency.contains(newText)){
							Var.selectedCoins.add(currency);
						}
					}
				}
				resetRecyclerView();
				return false;
			}
		});

	}

	public static void resetRecyclerView(){
		if(rvaList!= null)
			rvaList.notifyDataSetChanged();
	}
}
