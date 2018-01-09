package com.bitprofit.mono.bitprofit;

/**
 * Created by Christopher Thai on 1/9/2018.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.variables.Var;

import java.util.List;


public class RVACurrency extends RecyclerView.Adapter<RVACurrency.CurrencyName>{

	List<Var.AvailableCoin> currencies;

	/**
	 * RecyclerView Adapter that will set the cards
	 * @param currencies The currencies that will be shown
	 */
	RVACurrency(List<Var.AvailableCoin> currencies){
		this.currencies = currencies;
	}

	@Override
	public int getItemCount(){
		return (currencies==null)? 0:currencies.size();
	}

	@Override
	public CurrencyName onCreateViewHolder(ViewGroup viewGroup, int i){
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.currency_name,viewGroup,false);
		CurrencyName cvh = new CurrencyName(v);
		return cvh;
	}

	@Override
	public void onBindViewHolder(CurrencyName currencyViewHolder, int i){
		currencyViewHolder.name.setText(currencies.get(i).name);
		currencyViewHolder.abb.setText(currencies.get(i).abb);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView){
		super.onAttachedToRecyclerView(recyclerView);
	}

	/**
	 * ViewHolder to store itemViews of each card
	 */
	public static class CurrencyName extends RecyclerView.ViewHolder{
		CardView cv;
		TextView name,abb;

		CurrencyName(View itemView){
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			name = (TextView)itemView.findViewById(R.id.name);
			abb = (TextView)itemView.findViewById(R.id.abb);
		}
	}
}
