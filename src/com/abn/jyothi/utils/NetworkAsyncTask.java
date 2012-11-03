package com.abn.jyothi.utils;

import com.abn.jyothi.utils.Utility.Result;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public abstract class NetworkAsyncTask extends AsyncTask <Void, Void, Result>
{
	Dialog progress;
	Context context;
	String title;
	String message;
	boolean showDialog;
	
	public NetworkAsyncTask(Context context,String title,String message,boolean showDialog) 
	{
		super();
		
		this.context = context;
		this.title = title;
		this.message = message;
		this.showDialog = showDialog;
	}
	
	@Override
	protected void onPreExecute() 
	{
		if(showDialog)
			progress = ProgressDialog.show(context,title, message);
		
		super.onPreExecute();
	}

	@Override
	protected Result doInBackground(Void... arg0) 
	{
		try
		{
			if(!isNetworkAvailable())
				return Result.NETWORK_ERROR;
			
			perform();
			
			return Result.SUCCESS;
		}
		catch(Exception e)
		{
			return Result.ERROR;
		}
		
	}

	@Override
	protected void onPostExecute(Result result) 
	{
		if(showDialog)
			progress.dismiss();
		
		if(result == null || result == Result.ERROR)
			postError();
		else if(result == Result.NETWORK_ERROR)
			postNetworkError();
		else
			postSuccess();
		
		super.onPostExecute(result);
	}

	protected void postError()
	{
		Utility.getInstance().showToastLong(context, context.getResources().getString(com.abn.jyothi.R.string.general_error));
	}
	
	protected abstract void perform();	
	protected abstract void postSuccess();
	
	
	private void postNetworkError()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Network Error!");
		alertDialogBuilder
		.setMessage(context.getResources().getString(com.abn.jyothi.R.string.network_error))
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.dismiss();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	private boolean isNetworkAvailable()
	{
		ConnectivityManager connectivitymanager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        boolean connected = networkinfo != null && networkinfo.isAvailable()&& networkinfo.isConnected();
        
		return connected;
	}

}
