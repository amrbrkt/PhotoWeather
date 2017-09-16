package barakat.amr.photoweather.imageweather;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Uri> {

    private Uri fileUri;
    private FileOutputStream fOut;
    private ImageWeatherContract.View view;

    public BitmapWorkerTask(Uri fileUri, ImageWeatherContract.View view) throws FileNotFoundException {
        this.view = view;
        this.fileUri = fileUri;
        File file = new File(fileUri.getPath());
        fOut = new FileOutputStream(file);
    }

    @Override
    protected Uri doInBackground(Bitmap... params) {
        Bitmap bitmap = params[0];
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
        return fileUri;
    }

    @Override
    protected void onPostExecute(Uri fileUri) {
        view.showLoading(false);
        view.onTextDrawn(fileUri);
    }

}
