package com.bitprofit.mono.bitprofit.currencyinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyadder.CurrencyAdderActivity;
import com.bitprofit.mono.bitprofit.currencyinfo.async.FetchCurrencyInfo;
import com.bitprofit.mono.bitprofit.currencyinfo.async.FetchCurrencyInfoImage;
import com.bitprofit.mono.bitprofit.helper.Currency;
import com.bitprofit.mono.bitprofit.helper.Var;
import com.bitprofit.mono.bitprofit.helper.WriteJson;
import com.bitprofit.mono.bitprofit.main.MainActivity;

public class CurrencyInfoActivity extends AppCompatActivity{

	private String name;
	private String holding,profit,coins;
	private int color;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.currency_info);

		id = Integer.parseInt(getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_ID));
		Var.Coin c = Var.getCoin(id);
		name = Var.toFormatName(c.name);
		holding = getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_HOLDING);
		coins = ""+c.coins;
		profit = getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_PROFIT);
		color = Integer.parseInt(getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_COLOR));
		Var.log("Info for "+id+" "+name);

		initInfo();
		initButtons();
	}

	private void initInfo(){
		((TextView)findViewById(R.id.info_holding)).setText(holding);
		((TextView)findViewById(R.id.info_profit)).setText(profit);
		((TextView)findViewById(R.id.info_coin)).setText(coins);
		((TextView)findViewById(R.id.info_profit)).setTextColor(color);
		(new FetchCurrencyInfo(name,CurrencyInfoActivity.this)).execute();
		(new FetchCurrencyInfoImage(name,(ImageView)findViewById(R.id.info_icon))).execute();
	}

	private void initButtons(){
		((Button) findViewById(R.id.info_back)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				CurrencyInfoActivity.this.finish();
			}
		});

		((Button) findViewById(R.id.info_delete)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				showDeleteCoinConfirm();
			}
		});
		((Button) findViewById(R.id.info_add)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				Intent intent = new Intent(CurrencyInfoActivity.this, CurrencyAdderActivity.class);
				intent.putExtra(Var.INTENT_CURRENCY_INFO_ID,""+id);
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

	private void showDeleteCoinConfirm(){
		new AlertDialog.Builder(this)
				.setTitle("Confirm Deletion")
				.setMessage("Are you sure?")
				.setPositiveButton("Confirm",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog,int which){
						deleteCoin();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialogInterface, int i){
						//Do nothing
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	}

	private void deleteCoin(){
		Var.deleteCoin(id);
		Currency.deleteCurrency(id);
		save();
		CurrencyInfoActivity.this.finish();
	}
}
