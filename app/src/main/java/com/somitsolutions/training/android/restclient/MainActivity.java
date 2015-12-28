package com.somitsolutions.training.android.restclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class MainActivity extends Activity implements View.OnClickListener{

	Button mButtonGetWeather;
	Button mButtonGetPlace;
	public static double lat;
	public static double lng;

	ProgressDialog mProgressDialog;
	static Context mContext;
	LocationManager lm;
	private static MainActivity mainActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		mainActivity = this;
		mButtonGetPlace = (Button)findViewById(R.id.buttonPlace);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("Getting Data...Please wait...");
		
		mButtonGetPlace.setOnClickListener(this);


		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		////
		class myLocationlistener implements LocationListener {
			@Override
			public void onLocationChanged(Location location) {
				//int counter = 0;
				if (location != null) {
					lng = location.getLongitude();
					lat = location.getLatitude();
				}

			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub

			}
		}

		Location location = null;
		LocationListener ll = new myLocationlistener();
		try {

			// getting GPS status
			boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				// First get location from Network Provider
				if (isNetworkEnabled) {
					lm.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,  35000,  0, ll);
					Log.d("Network", "Network");
					if (lm != null) {
						location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							lat = location.getLatitude();
							lng = location.getLongitude();
							Log.i("Network Latitude :", Double.toString(lat));
						}
					}
				}
				//get the location by gps
				if (isGPSEnabled) {
					if (location == null) {
						lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,35000, 0, ll);
						Log.d("GPS Enabled", "GPS Enabled");
						if (lm != null) {location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								lat = location.getLatitude();
								lng = location.getLongitude();
								Log.i("GPS Location: ", Double.toString(lat));
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(v.equals(mButtonGetPlace)){
			LinkedHashMap<Object, Object> data = new LinkedHashMap<Object,Object>();
			//String pinStr = mPin.getText().toString();
			//Log.i("Pin", pinStr);
			data.put("lat", lat/*22.5735314*/);
			data.put("lng", lng/*88.4331189*/);
			//Toast.makeText(getApplicationContext(), "Lat:" + Double.toString(lat) + "lng:" + Double.toString(lng), Toast.LENGTH_LONG).show();
			data.put("username", "sommukhopadhyay");
			
			HTTPAsyncTask httpAsyncTaskGetPlace = new HTTPAsyncTask(mContext,new CallBack(){

				@Override
				public void onProgress() {
				// TODO Auto-generated method stub
				mProgressDialog.show();
				}
				@Override
				public void onResult(String result) {
				mProgressDialog.dismiss();

				// TODO Auto-generated method stub
				//
				if (!result.equals("")) {
						try {
						JSONObject jsonObject = new JSONObject(result);
						Log.i("Data", jsonObject.toString());
						if (jsonObject.length() != 0){
							JSONArray poi = new JSONArray(jsonObject.getString("poi"));
							if(poi.length() != 0){

								Intent intentDisplay = new Intent(getApplicationContext(), DisplayActivity.class);
								intentDisplay.putExtra("jsonArray", poi.toString());
								startActivity(intentDisplay);
								/*for (int i = 0; i < poi.length(); i++) {
									JSONObject poiData =
											poi.getJSONObject(i);
									Toast.makeText(getBaseContext(),
											poiData.getString("typeName") + " - " +
													poiData.getString("name"), Toast.LENGTH_LONG).show();
								}*/
							}
							else{
								Toast.makeText(getApplicationContext(), "No Data Available...", Toast.LENGTH_LONG).show();
							}
						}
						else{
							Toast.makeText(getApplicationContext(), "No Data Available...", Toast.LENGTH_LONG).show();
						}

					 }catch(JSONException e){
						 // TODO Auto-generated catch block
						 e.printStackTrace();
					 }
				 }
					//no data available
				}
				 
				@Override
				public void onCancel() {
				// TODO Auto-generated method stub
					
				}
			},data , null, "GET");
			httpAsyncTaskGetPlace.execute("http://api.geonames.org/findNearbyPOIsOSMJSON");
		}
	}
}
