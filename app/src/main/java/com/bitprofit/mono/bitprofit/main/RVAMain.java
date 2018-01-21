package com.bitprofit.mono.bitprofit.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.helper.Currency;
import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.currencyinfo.async.FetchCurrencyInfo;
import com.bitprofit.mono.bitprofit.currencyinfo.async.FetchCurrencyInfoImage;
import com.bitprofit.mono.bitprofit.helper.Var;

import java.util.List;

/**
 * Updates Recycler View
 * Created by Christopher Thai on 12/20/2017.
 */

public class RVAMain extends RecyclerView.Adapter<RVAMain.CurrencyViewHolder>{

	List<Currency> currencies;
	static RelativeLayout layout;
	static MainActivity activity;

	/**
	 * RecyclerView Adapter that will set the cards
	 * @param currencies The currencies that will be shown
	 */
	public RVAMain(List<Currency> currencies,RelativeLayout rlayout,MainActivity activity){
		this.currencies = currencies;
		layout = rlayout;
		this.activity = activity;
	}

	@Override
	public int getItemCount(){
		Log.i("adapter",""+currencies.size());
		return (currencies==null)? 0:currencies.size();
	}

	@Override
	public CurrencyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card,viewGroup,false);
		CurrencyViewHolder cvh = new CurrencyViewHolder(v);
		return cvh;
	}

	@Override
	public void onBindViewHolder(CurrencyViewHolder currencyViewHolder, int i){
		currencyViewHolder.name.setText(currencies.get(i).getName());
		currencyViewHolder.price.setText(currencies.get(i).getPrice());
		currencyViewHolder.total.setText(currencies.get(i).getTotal());
		currencyViewHolder.profit.setText(currencies.get(i).getProfit());
		//ASyncTask FetchImage will update the image if it hasn't finished
		if(currencies.get(i).getIcon()==null)
			currencies.get(i).needsReload(currencyViewHolder.image);
		else
			currencyViewHolder.image.setImageDrawable(currencies.get(i).getIcon());
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView){
		super.onAttachedToRecyclerView(recyclerView);
	}

	/**
	 * ViewHolder to store itemViews of each card
	 */
	public static class CurrencyViewHolder extends RecyclerView.ViewHolder{
		CardView cv;
		TextView name,price,total,profit;
		ImageView image;
		static FetchCurrencyInfo info;
		static FetchCurrencyInfoImage infoImage;

		CurrencyViewHolder(final View itemView){
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			name = (TextView)itemView.findViewById(R.id.name);
			price = (TextView)itemView.findViewById(R.id.price);
			total = (TextView)itemView.findViewById(R.id.total);
			profit = (TextView)itemView.findViewById(R.id.profit);
			image = (ImageView)itemView.findViewById(R.id.icon);
			cv.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view){
					activity.showInfo(name.getText().toString());
				}
			});
		}
	}
}
