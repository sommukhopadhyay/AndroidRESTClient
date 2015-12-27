package com.somitsolutions.training.android.restclient;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayActivity extends ListActivity{
    ArrayList<POIDataModel> mDisplayArray = new ArrayList<POIDataModel>();
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mListView = getListView();

        String displayData = getIntent().getStringExtra("jsonArray");

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


}
