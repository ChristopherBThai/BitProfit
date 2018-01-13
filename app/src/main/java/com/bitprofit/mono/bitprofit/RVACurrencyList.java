package com.bitprofit.mono.bitprofit;

/**
 * Recycler view adapter for all the list of currencies
 * Created by Christopher Thai on 1/9/2018.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitprofit.mono.bitprofit.async.FetchCurrencyListImage;
import com.bitprofit.mono.bitprofit.variables.Var;

import java.util.List;


public class RVACurrencyList extends RecyclerView.Adapter<RVACurrencyList.CurrencyName>{

	List<Var.AvailableCoin> currencies;
	static RelativeLayout nextLayout,currentLayout;

	/**
	 * RecyclerView Adapter for adding currencies
	 * @param cLayout current layout
	 * @param nLayout next layout
	 * @param currencies list of available currencies
	 */
	RVACurrencyList(List<Var.AvailableCoin> currencies, RelativeLayout nLayout, RelativeLayout cLayout){
		this.currencies = currencies;
		this.nextLayout = nLayout;
		this.currentLayout = cLayout;
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
		static FetchCurrencyListImage fetchImage;
		static ImageView imageView;

		CurrencyName(View itemView){
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			name = (TextView)itemView.findViewById(R.id.name);
			abb = (TextView)itemView.findViewById(R.id.abb);
			cv.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view){
					nextLayout.setVisibility(View.VISIBLE);
					currentLayout.setVisibility(View.INVISIBLE);
					((TextView)nextLayout.findViewById(R.id.search2_name)).setText(name.getText());
					((TextView)nextLayout.findViewById(R.id.search2_symbol)).setText(abb.getText());
					grabImage(name);
				}
			});
		}

		static void grabImage(TextView name){
			if(fetchImage==null){
				imageView = ((ImageView) nextLayout.findViewById(R.id.search2_icon));
			}else{
				fetchImage.cancel(true);
			}
			fetchImage = new FetchCurrencyListImage(name.getText().toString(), imageView);
			fetchImage.execute();
		}
	}

	public void setCurrencies(List<Var.AvailableCoin> currencies){
		this.currencies = currencies;
	}
}
