package puti.po.hakathon;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class GPSTracker {
    private Context context;
    private LocationManager manager;
    private Double lathitude = 0.0;
    private Double longitude = 0.0;
    private ICoordinatesSender _interface;

    public GPSTracker(ICoordinatesSender _interface, Context _context) {
        this._interface = _interface;
        this.context = _context;
    }

    public void startGPSSearch() {
        /*Intent intent = new Intent(this, CameraPreviewActivity.class);
        startActivity(intent);*/
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = manager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        }
        else {
            Toast.makeText(context,
                    "Starting GPS Search",
                    Toast.LENGTH_LONG).show();
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = manager.getLastKnownLocation(locationProvider);
            if(lastKnownLocation != null) {
                updateLocation(lastKnownLocation);
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);

        }

    }

    public Double getLathitude() {
        return lathitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void updateLocation(Location location) {
        if (location != null) {
            double _lathitude = location.getLatitude();
            double _longitude = location.getLongitude();

            double _shLathitude = new BigDecimal(_lathitude)
                    .setScale(4, RoundingMode.FLOOR).doubleValue();
            double _shLongitude = new BigDecimal(_longitude)
                    .setScale(4, RoundingMode.FLOOR).doubleValue();

            double shLathitude = new BigDecimal(lathitude)
                    .setScale(4, RoundingMode.FLOOR).doubleValue();
            double shLongitude = new BigDecimal(longitude)
                    .setScale(4, RoundingMode.FLOOR).doubleValue();

            if(_shLathitude == shLathitude && _shLongitude == shLongitude) {
                return;
            }
            lathitude = _lathitude;
            longitude = _longitude;
            /*String slathitude = lathitude.toString();
            String slongitude = longitude.toString();
            Toast.makeText(context,
                    "Your Location is - \nLat: " + slathitude + "\nLong: " + slongitude,
                    Toast.LENGTH_LONG).show();*/
            _interface.sendMyLocation(lathitude, longitude);
        } else {
            Toast.makeText(context,
                    "Sorry, location unavailable",
                    Toast.LENGTH_LONG).show();
        }
    }



}