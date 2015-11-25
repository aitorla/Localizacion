package com.example.aitor.localizacion;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private LocationManager locMgr;
    private LocationListener onLocationChange;
    private TextView txtLongitud, txtLatitud;
    private ToggleButton tbActivar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtLatitud = (TextView) findViewById(R.id.latitud);
        txtLongitud = (TextView) findViewById(R.id.longitud);
        tbActivar = (ToggleButton) findViewById(R.id.toggleButton);
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;}


        android.location.Location location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
            location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            txtLongitud.setText("Longitud:" + location.getLongitude());
            txtLatitud.setText("Latitud:" + location.getLatitude());
        } else {
            txtLongitud.setText("Longitud no disponible:");
            txtLatitud.setText("Latitud no disponible:");
        }


        onLocationChange = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                txtLongitud.setText("Longitud:" + location.getLongitude());
                txtLatitud.setText("Latitud:" + location.getLatitude());
                Log.i("LOG", "Localización modificada ");
                Toast.makeText(MainActivity.this, "Proveedor localizacion"+ txtLongitud.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("LOG", "Proveedor localización " + provider + ". Ha cambiado el estado a " + status);

            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("LOG", "Proveedor localización " + provider + ". Se ha habilitado");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("LOG", "Proveedor localización " + provider + ". Se ha deshabilitado");

            }
        };


        tbActivar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;}

                if (tbActivar.isChecked()) {

                    locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, onLocationChange);
                    }
                    else
                    locMgr.removeUpdates(onLocationChange);
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

