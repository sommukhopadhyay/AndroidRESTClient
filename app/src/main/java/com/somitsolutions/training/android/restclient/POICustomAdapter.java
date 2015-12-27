package com.somitsolutions.training.android.restclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by som on 26/12/15.
 */
public class POICustomAdapter extends ArrayAdapter<POIDataModel> {

    private Context mContext;
    List<POIDataModel> mDataList;

    public POICustomAdapter(Context context, int resource,
                                  List<POIDataModel> objects) {
        super(context, R.layout.singleitemlayout, objects);
        // TODO Auto-generated constructor stub
        mContext = context;
        mDataList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.singleitemlayout, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView typeName = (TextView)convertView.findViewById(R.id.typeName);

        POIDataModel temp = mDataList.get(position);

        name.setText(temp.getName());
        typeName.setText(temp.getTypeName());

        return convertView;
    }
}
