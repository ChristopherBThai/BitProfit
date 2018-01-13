package com.bitprofit.mono.bitprofit;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Updates Recycler View
 * Created by Christopher Thai on 12/20/2017.
 */

public class RVAMain extends RecyclerView.Adapter<RVAMain.CurrencyViewHolder>{

	List<Currency> currencies;

	/**
	 * RecyclerView Adapter that will set the cards
	 * @param currencies The currencies that will be shown
	 */
	RVAMain(List<Currency> currencies){
		this.currencies = currencies;
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
		currencyViewHolder.name.setText(currencies.get(i).name);
		currencyViewHolder.price.setText(currencies.get(i).getPrice());
		currencyViewHolder.total.setText(currencies.get(i).getTotal());
		currencyViewHolder.profit.setText(currencies.get(i).getProfit());
		//ASyncTask FetchImage will update the image if it hasn't finished
		if(currencies.get(i).icon==null)
			currencies.get(i).needsReload(currencyViewHolder.image);
		else
			currencyViewHolder.image.setImageDrawable(currencies.get(i).icon);
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

		CurrencyViewHolder(View itemView){
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			name = (TextView)itemView.findViewById(R.id.name);
			price = (TextView)itemView.findViewById(R.id.price);
			total = (TextView)itemView.findViewById(R.id.total);
			profit = (TextView)itemView.findViewById(R.id.profit);
			image = (ImageView)itemView.findViewById(R.id.icon);
		}
	}
}
