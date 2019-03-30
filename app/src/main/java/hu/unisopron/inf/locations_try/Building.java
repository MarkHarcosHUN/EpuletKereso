package hu.unisopron.inf.locations_try;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class Building {
    private Marker marker;
    private String name;
    private double xPos;
    private double yPos;
    private int pictureID;
    private String about;

    public Building(String name){
        this.name=name;
    }
    public Building(String name, double xPos,double yPos,String about,int pictureID){
        this.name=name;
        this.xPos=xPos;
        this.yPos=yPos;
        this.about=about;
        this.pictureID=pictureID;
    }

    public String getName() {
        return name;
    }

    public LatLng getPos(){
        return new LatLng(xPos,yPos);
    }
    public void setMarker(Marker m){
        marker=m;
    }

    @Override
    public String toString() {
        return name;
    }

    public Marker getMarker() {
        return marker;
    }

    public int getPictureID() {
        return pictureID;
    }

    public String getAbout() {
        return about;
    }
}
