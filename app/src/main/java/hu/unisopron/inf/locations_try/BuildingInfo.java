package hu.unisopron.inf.locations_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hu.unisopron.inf.locations_try.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuildingInfo extends AppCompatActivity {
    CircleImageView buildingImage;
    TextView buildingText,buildingTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_info);
        buildingImage=findViewById(R.id.buildingImage);
        buildingText=findViewById(R.id.buildingText);
        buildingTitle=findViewById(R.id.buildingTitle);
        Intent i=getIntent();
        buildingImage.setImageResource(i.getIntExtra("building_pictureID",0));
        buildingText.setText(i.getStringExtra("building_about"));
        buildingTitle.setText(i.getStringExtra("building_title"));
    }

    public void backButtonClicked(View view) {
        finish();
    }
}
