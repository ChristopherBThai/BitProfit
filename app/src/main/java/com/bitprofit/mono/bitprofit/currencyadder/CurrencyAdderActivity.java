package com.bitprofit.mono.bitprofit.currencyadder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyadder.async.FetchCurrencyAdderImage;
import com.bitprofit.mono.bitprofit.currencyadder.async.FetchCurrencyAdderInfo;
import com.bitprofit.mono.bitprofit.currencylist.CurrencyListActivity;
import com.bitprofit.mono.bitprofit.helper.Var;
import com.bitprofit.mono.bitprofit.helper.WriteJson;
import com.bitprofit.mono.bitprofit.main.MainActivity;
import com.bitprofit.mono.bitprofit.main.async.FetchData;

public class CurrencyAdderActivity extends AppCompatActivity{

	private int id;
	private boolean isInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.currency_adder);

		String name = getIntent().getStringExtra(Var.INTENT_CURRENCY_LIST_NAME);
		String stringID = getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_ID);
		if(stringID!=null&&!stringID.equals("")){
			id = Integer.parseInt(stringID);
			isInfo = true;
		}else
			isInfo = false;

		grabInfo(name);
		grabImage(name);
		initButtons();
	}

	private void grabInfo(String name){
		FetchCurrencyAdderInfo info = new FetchCurrencyAdderInfo(name,this);
		info.execute();
	}

	public void setInfo(String name,String symbol,String price){
		((TextView)findViewById(R.id.search2_name)).setText(name);
		((TextView)findViewById(R.id.search2_symbol)).setText(symbol);
		//((TextView)findViewById(R.id.search2_price)).setText(price);
	}

	private void grabImage(String name){
		FetchCurrencyAdderImage image = new FetchCurrencyAdderImage(name,(ImageView)findViewById(R.id.search2_icon));
		image.execute();
	}

	private void initButtons(){
		((Button)findViewById(R.id.search2_back)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//Go back to search screen
				CurrencyAdderActivity.this.finish();
			}
		});

		((Button)findViewById(R.id.search2_submit)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//Add new currency
				String name = ((TextView)findViewById(R.id.search2_name)).getText().toString();
				name = Var.toFormatName(name);
				double initial = Double.valueOf(((EditText)findViewById(R.id.search2_usd)).getText().toString());
				double amount = Double.valueOf(((EditText)findViewById(R.id.search2_coin)).getText().toString());
				if(isInfo)
					Var.addToCoin(id,initial,amount);
				else
					Var.addNewCoin(name,amount,initial);
				save();
				startActivity(new Intent(CurrencyAdderActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
	}

	private void save(){
		try{
			WriteJson write = new WriteJson(openFileOutput(Var.FILENAME, Context.MODE_PRIVATE));
			write.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
