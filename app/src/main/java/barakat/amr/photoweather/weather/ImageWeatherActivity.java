package barakat.amr.photoweather.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileNotFoundException;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.R;
import barakat.amr.photoweather.base.BaseActivity;
import barakat.amr.photoweather.data.model.Weather;
import butterknife.BindView;


public class ImageWeatherActivity extends BaseActivity implements ImageWeatherContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.imageView)
    SimpleDraweeView imageView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private GoogleApiClient googleApiClient;
    private ImageWeatherPresenter presenter = new ImageWeatherPresenter();
    private Uri fileUri;

    @Override
    protected int getActivityTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_weather;
    }

    @Override
    protected boolean isUpEnabled() {
        return true;
    }

    @Override
    protected void afterActivityInflation() {
        presenter.attachView(this);
        if (getIntent() != null) {
            fileUri = Uri.parse(getIntent().getStringExtra(Constants.IMAGE_URI));
            //imageView.setImageURI(fileUri);
            presenter.getLocation(this);
        } else {
            Toast.makeText(activity, "An Error Occurred", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length <= 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLocationClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onWeatherUpdate(Weather weather) {
        Toast.makeText(activity, String.valueOf(weather.getMain().getTempMax()), Toast.LENGTH_LONG).show();
        Bitmap newbitmap;
        try {
            newbitmap = ImageWeatherUtils.writeOnImage(getContentResolver().openInputStream(fileUri),
                    String.valueOf(weather.getMain().getTempMax()),
                    weather.getWeather().get(0).getMain(),
                    weather.getSys().getCountry());
            imageView.setImageBitmap(newbitmap);
        } catch (FileNotFoundException e) {
            Toast.makeText(activity, "Failed to draw text", Toast.LENGTH_SHORT).show();
            imageView.setImageURI(fileUri);
        }
    }

    @Override
    public void onWeatherFailed(String cause) {
        Log.d("Weather Failed", cause);
        Toast.makeText(activity, "Weather failed: " + cause, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
            presenter.getWeatherOf(lat, lon);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(activity, "Google Apis Connection Suspend", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(activity, "Google Apis Connection failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }
}
