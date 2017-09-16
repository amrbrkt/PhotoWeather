package barakat.amr.photoweather.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.OutputStream;


public class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Uri> {

    private Uri fileUri;
    private OutputStream fOut;
    private DataManager manager;

    public BitmapWorkerTask(Context context, Uri fileUri, DataManager manager) throws FileNotFoundException {
        this.manager = manager;
        this.fileUri = fileUri;
        fOut = context.getContentResolver().openOutputStream(fileUri);
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
