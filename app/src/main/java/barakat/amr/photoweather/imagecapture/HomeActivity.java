package barakat.amr.photoweather.imagecapture;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.R;
import barakat.amr.photoweather.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements ImageCaptureContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyView)
    TextView emptyView;

    private ImageCapturePresenter presenter = new ImageCapturePresenter();
    private Uri fileUri;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.app_name;
    }

    @Override
    protected void afterActivityInflation() {
        presenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.requestPermissions(this);
    }

    @Override
    protected boolean isUpEnabled() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.startWeatherActivity(activity, fileUri);
        } else {
            Toast.makeText(activity, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fab)
    void captureImage() {
        presenter.checkCamera(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS:
                if (grantResults.length <= 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Need to access Camera!", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length <= 0 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length <= 0 && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Need access storage write!", Toast.LENGTH_SHORT).show();
                } else if (grantResults.length <= 0 && grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Need access storage read!", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.getSavedImages(this);
                }
        }
    }

    @Override
    public void onCaptureReady(boolean isReady, Uri fileUri) {
        if (isReady) {
            this.fileUri = fileUri;
            presenter.openCamera(this, fileUri);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocalDataLoaded(List<String> paths) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        ImagesAdapter adapter = new ImagesAdapter(this, paths);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLocalDataIsEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

}
