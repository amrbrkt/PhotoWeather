package barakat.amr.photoweather.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Uri> {

    private Uri fileUri;
    private FileOutputStream fOut;
    private DataManager manager;

    public BitmapWorkerTask(Uri fileUri, DataManager manager) throws FileNotFoundException {
        this.manager = manager;
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
        manager.onDrawnOut(fileUri);
    }

}
