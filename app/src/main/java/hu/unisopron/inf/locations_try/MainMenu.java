package hu.unisopron.inf.locations_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hu.unisopron.inf.locations_try.R;

public class MainMenu extends AppCompatActivity {
public static final int SOPRON=0;
public static final int TATA=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    public void showSopron(View view) {
        Intent i=new Intent(this,MapsActivity.class);
        startActivity(i);
    }

    public void sopronClicked(View view) {
        start(SOPRON);
    }
    private void start(int place){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        Intent i=new Intent(this,MapsActivity.class);
        i.putExtra("PLACE",place);
        startActivity(i);
    }

    public void tataClicked(View view) {
        start(TATA);
    }

    public void showAboutUs(View view) {
        Intent i=new Intent(this,AboutUs.class);
        startActivity(i);
    }
}
