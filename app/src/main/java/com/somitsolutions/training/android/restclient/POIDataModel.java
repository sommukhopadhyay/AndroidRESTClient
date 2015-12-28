package com.somitsolutions.training.android.restclient;

/**
 * Created by som on 26/12/15.
 */
public class POIDataModel {
    private String name;
    private String typeName;
    private double lat;
    private double lng;

    public POIDataModel(){
        this.name = "";
        this.typeName="";
    }

    public POIDataModel(String name, String typeName){
        this.name = name;
        this.typeName = typeName;
    }

    public String getName(){
        return name;
    }

    public String getTypeName(){
        return typeName;
    }

    public double getLat(){return lat;}
    public double getLng(){return lng;}

    public  void setName(String name){
        this.name = name;
    }
    public  void setTypeName(String typeName){
        this.typeName = typeName;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public void setlng(double lng){
        this.lng = lng;
    }
}
