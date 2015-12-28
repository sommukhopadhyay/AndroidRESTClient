package com.somitsolutions.training.android.restclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayActivity extends ListActivity implements View.OnClickListener{
    ArrayList<POIDataModel> mDisplayArray = new ArrayList<POIDataModel>();
    ListView mListView;
    Button mBtnShowOnGoogleMap;
    String displayData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mListView = getListView();
        mBtnShowOnGoogleMap = (Button)findViewById(R.id.showOnMap);
        mBtnShowOnGoogleMap.setOnClickListener(this);
        displayData = getIntent().getStringExtra("jsonArray");

        try {
            JSONArray jsonDisplayData = new JSONArray(displayData);

            for (int i = 0; i <jsonDisplayData.length(); i++){
                JSONObject jsonData = jsonDisplayData.getJSONObject(i);
                POIDataModel dataModelTemp = new POIDataModel();
                dataModelTemp.setName(jsonData.getString("name"));
                dataModelTemp.setTypeName(jsonData.getString("typeName"));
                mDisplayArray.add(dataModelTemp);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final POICustomAdapter customArrayAdapter = new POICustomAdapter(getApplicationContext(), R.layout.singleitemlayout,mDisplayArray);
        setListAdapter(customArrayAdapter);
    }


    @Override
    public void onClick(View view) {
        if(view.equals(mBtnShowOnGoogleMap)){
            Intent displayMapIntent = new Intent(getApplicationContext(), MapsActivity.class);
            displayMapIntent.putExtra("MapData",displayData);
            startActivity(displayMapIntent);

        }
    }
}
