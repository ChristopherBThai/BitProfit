package com.bitprofit.mono.bitprofit.async;

import android.os.AsyncTask;
import android.util.JsonWriter;

import com.bitprofit.mono.bitprofit.variables.Var;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Saves the user's info
 * Created by Christopher Thai on 12/26/2017.
 */

public class WriteJson extends AsyncTask<Void,Void,Void>{

	public final String FILENAME = "save.json";
	FileOutputStream out;

	public WriteJson(FileOutputStream out){
		this.out = out;
	}


	@Override
	protected Void doInBackground(Void... voids){
		try{
			JsonWriter writer = new JsonWriter(new OutputStreamWriter(out,"UTF-8"));
			writer.setIndent("  ");
			writeMessageArray(writer);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid){
		super.onPostExecute(aVoid);
	}

	private void writeMessageArray(JsonWriter writer){
		try{
			writer.beginArray();
			while(Var.inUse);
			Var.lock();
			for(Var.Coin c : Var.coins){
				writeMessage(writer,c);
			}
			writer.endArray();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Var.unlock();
		}
	}

	private void writeMessage(JsonWriter writer,Var.Coin c){
		try{
			writer.beginObject();
			writer.name("name").value(c.name);
			writer.name("coins").value(c.coins);
			writer.name("initial").value(c.initial);
			writer.endObject();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
