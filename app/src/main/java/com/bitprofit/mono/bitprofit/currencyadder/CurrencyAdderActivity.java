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
import android.widget.Toast;

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
			name = Var.getCoin(id).name;
			isInfo = true;
			Var.log("Addint to existing currency for "+name);
		}else{
			isInfo = false;
			Var.log("Adding a new coin "+name);
		}

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
				String temp = ((EditText)findViewById(R.id.search2_usd)).getText().toString();
				if(temp.isEmpty()||temp.equals("")){
					showErrorMessage();
					return;
				}
				double initial = Double.valueOf(temp);
				temp = ((EditText)findViewById(R.id.search2_coin)).getText().toString();
				if(temp.isEmpty()||temp.equals("")){
					showErrorMessage();
					return;
				}
				double amount = Double.valueOf(temp);
				if(isInfo){
					Var.addToCoin(id, initial, amount);
					Var.log("Adding to coin "+name);
				}else{
					Var.addNewCoin(name, amount, initial);
					Var.log("Adding new coin "+name);
				}
				save();
				startActivity(new Intent(CurrencyAdderActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});
	}

	private void save(){
		try{
			WriteJson write = new WriteJson(openFileOutput(Var.FILENAME, Context.MODE_PRIVATE));
			write.execute();
			Var.log("Saving");
		}catch(Exception e){
			e.printStackTrace();
			Var.error("Error saving");
		}
	}

	private void showErrorMessage(){
		Toast.makeText(this,"Empty variables!",Toast.LENGTH_SHORT).show();
	}
}
