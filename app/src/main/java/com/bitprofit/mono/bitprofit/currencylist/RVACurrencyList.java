package com.bitprofit.mono.bitprofit.currencylist;

/**
 * Recycler view adapter for all the list of currencies
 * Created by Christopher Thai on 1/9/2018.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.R;
import com.bitprofit.mono.bitprofit.helper.Var;

import java.util.List;


public class RVACurrencyList extends RecyclerView.Adapter<RVACurrencyList.CurrencyName>{

	List<Var.AvailableCoin> currencies;
	static CurrencyListActivity context;

	/**
	 * RecyclerView Adapter for adding currencies
	 * @param currencies list of available currencies
	 */
	public RVACurrencyList(List<Var.AvailableCoin> currencies, CurrencyListActivity context){
		this.currencies = currencies;
		this.context = context;
	}

	@Override
	public int getItemCount(){
		return (currencies==null)? 0:currencies.size();
	}

	@Override
	public CurrencyName onCreateViewHolder(ViewGroup viewGroup, int i){
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.currency_list_name,viewGroup,false);
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
			cv.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view){
					context.selectCurrency(name.getText().toString());
				}
			});
		}
	}

	public void setCurrencies(List<Var.AvailableCoin> currencies){
		this.currencies = currencies;
	}
}
