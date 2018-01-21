package com.bitprofit.mono.bitprofit.currencyinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyinfo.async.FetchCurrencyInfo;
import com.bitprofit.mono.bitprofit.currencyinfo.async.FetchCurrencyInfoImage;
import com.bitprofit.mono.bitprofit.helper.Var;

public class CurrencyInfoActivity extends AppCompatActivity{

	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.currency_info);

		name = getIntent().getStringExtra(Var.INTENT_CURRENCY_INFO_NAME);

		initInfo();
		initButtons();
	}

	private void initInfo(){
		(new FetchCurrencyInfo(name,CurrencyInfoActivity.this)).execute();
		(new FetchCurrencyInfoImage(name,(ImageView)findViewById(R.id.info_icon))).execute();
	}

	private void initButtons(){
		((Button)findViewById(R.id.info_back)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				CurrencyInfoActivity.this.finish();
			}
		});
	}
}
