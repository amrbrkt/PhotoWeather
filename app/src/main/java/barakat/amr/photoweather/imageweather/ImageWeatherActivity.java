package barakat.amr.photoweather.imageweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileNotFoundException;
import java.util.List;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.R;
import barakat.amr.photoweather.base.BaseActivity;
import barakat.amr.photoweather.data.model.Weather;
import butterknife.BindView;
import butterknife.OnClick;


public class ImageWeatherActivity extends BaseActivity implements ImageWeatherContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.share_fab)
    FloatingActionButton shareButton;

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
        Log.d("showLoading", " " + isLoading);
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            shareButton.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            shareButton.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLocationClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onWeatherUpdate(Weather weather) {
        try {
            presenter.drawOnImage(this, fileUri, weather);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTextDrawn(Uri fileUri) {
        Log.d("onTextDrawn", fileUri.toString());
        Glide.with(this)
                .load(fileUri)
                .into(imageView);
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

    @OnClick(R.id.share_fab)
    void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        sharingIntent.setType("image/png");

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(sharingIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity after checking existing of implicit activities
        if (isIntentSafe) {
            startActivity(Intent.createChooser(sharingIntent, "Send image using.."));
        }

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
