package hu.unisopron.inf.locations_try;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import hu.unisopron.inf.locations_try.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<Building> buildings;
    private GoogleMap mMap;
    private LatLngBounds.Builder builder;
    private Marker pufferMarker;

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(!isNetworkConnected()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage("Ellenőrizze az internetkapcsolatát!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        spinner =findViewById(R.id.spinner);

        populateBuildings(getIntent().getIntExtra("PLACE",0));

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    protected void onStart() {
        super.onStart();
        setupSpinner();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                newInfoActivity(arg0);
            }
        });
        builder = new LatLngBounds.Builder();
        for(int i=1;i<buildings.size();i++){
            Building b=buildings.get(i);
            builder.include(b.getPos());
        }

        addMarkers();



    }
    private void moveToCurrentLocation(LatLng currentLocation)
    {
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
       // mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
    }
    private void addMarkers(){
        float color=10;
        for(Building b:buildings){
           Marker marker= mMap.addMarker(new MarkerOptions()
                    .position(b.getPos())
                    .title(b.getName()).snippet("Kattints a bővebb információkért!")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(color+=10)));
           marker.setTag(b);
           b.setMarker(marker);
        }

    }
    private void setupSpinner(){
        ArrayAdapter<Building> adapter = new ArrayAdapter<Building>(this, android.R.layout.simple_spinner_dropdown_item, buildings);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0) {
                    LatLngBounds llb=builder.build();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(llb,100));
                    float campos=mMap.getCameraPosition().zoom;
                    if(campos>17) mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(llb.getCenter(), 17)); // ha túl közellennének az épületek akkoris madáltávlatból lássuk
                    if(pufferMarker!=null) { // összes mutatásánál infoablak reset
                        pufferMarker.hideInfoWindow();
                        pufferMarker = null;
                    }
                } else {
                    Building b = (Building) parentView.getSelectedItem();
                    moveToCurrentLocation(b.getPos());
                    pufferMarker=b.getMarker();
                    pufferMarker.showInfoWindow();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    private void populateBuildings(int place){
        buildings=new ArrayList<>();
        buildings.add(new Building("Összes"));
        switch (place){
            case MainMenu.SOPRON: populateSopron(); break;
            case MainMenu.TATA: populateTata(); break;
        }

    }
    private void populateTata(){
        buildings.add(new Building("Volt Piarista Rendház",47.643405, 18.319075,getResources().getString(R.string.building_tata),0));
    }
    private void populateSopron(){
        buildings.add(new Building("A épület",47.681043, 16.577883,getResources().getString(R.string.building_a),0));
        buildings.add(new Building("B épület",47.680260, 16.577294,getResources().getString(R.string.building_b),0));
        buildings.add(new Building("C épület",47.679398, 16.577148,getResources().getString(R.string.building_c),0));
        buildings.add(new Building("D épület",47.680782, 16.576248,getResources().getString(R.string.building_d),0));
        buildings.add(new Building("E épület",47.680902, 16.574425,getResources().getString(R.string.building_e),0));
        buildings.add(new Building("F épület",47.679988, 16.575624,getResources().getString(R.string.building_f),0));

        buildings.add(new Building("GT épület",47.680104, 16.578476,getResources().getString(R.string.building_gt),0));

        buildings.add(new Building("Benedek Elek Pedagógiai Kar",47.687194, 16.583393,getResources().getString(R.string.building_bpk),0));

        buildings.add(new Building("Alkalmazott Művészeti Intézet",47.680579, 16.585790,getResources().getString(R.string.building_ami),R.drawable.building_ami));
        buildings.add(new Building("Lámfalussy Sándor Közgazdaságtudományi Kar",47.681429, 16.587133,getResources().getString(R.string.building_lskk),0));

        buildings.add(new Building("Idegennyelvi Központ",47.681628, 16.587259,getResources().getString(R.string.building_ik),0));

    }
    private void newInfoActivity(Marker marker){
        Intent i=new Intent(this,BuildingInfo.class);
        Building building=(Building)marker.getTag();
        i.putExtra("building_about",building.getAbout());
        i.putExtra("building_pictureID",building.getPictureID());
        i.putExtra("building_title",building.getName());
        startActivity(i);
        marker.hideInfoWindow();

    }
}
