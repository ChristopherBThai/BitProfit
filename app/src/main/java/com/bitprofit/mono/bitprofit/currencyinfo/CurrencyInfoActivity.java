package com.bitprofit.mono.bitprofit.currencyinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.currency_info);

		id = Integer.parseInt(getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_ID));
		name = Var.toFormatName(Var.getCoin(id).name);
		Var.log("Info for "+id+" "+name);

		initInfo();
		initButtons();
	}

	private void initInfo(){
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
				Var.deleteCoin(id);
				Currency.deleteCurrency(id);
				save();
				CurrencyInfoActivity.this.finish();
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
}
