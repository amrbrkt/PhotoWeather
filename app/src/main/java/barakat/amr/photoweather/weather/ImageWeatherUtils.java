package barakat.amr.photoweather.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.InputStream;

public class ImageWeatherUtils {
    public static Bitmap writeOnImage(InputStream stream, String temp, String condition, String location) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;

        Bitmap bm = BitmapFactory.decodeStream(stream, null, option);

        Bitmap.Config config = bm.getConfig();
        if (config == null) {
            config = android.graphics.Bitmap.Config.ARGB_8888;
        }

        Bitmap newImage = bm.copy(config, true);

        Canvas c = new Canvas(newImage);
        c.drawBitmap(bm, 0, 0, null);

        Paint tempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tempPaint.setColor(Color.WHITE);
        tempPaint.setStyle(Paint.Style.FILL);
        tempPaint.setTextSize(200);
        tempPaint.setShadowLayer(3f, -2f, 2f, Color.BLACK);

        Rect tempRect = new Rect();
        tempPaint.getTextBounds(temp, 0, temp.length(), tempRect);
        int x = 25;
        int y = (newImage.getHeight() - tempRect.height() * 3);
        c.drawText(temp + "\u00b0 C", x, y, tempPaint);


        Paint conditionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        conditionPaint.setColor(Color.WHITE);
        conditionPaint.setStyle(Paint.Style.FILL);
        conditionPaint.setTextSize(100);
        conditionPaint.setShadowLayer(3f, -2f, 2f, Color.BLACK);

        Rect conditionRect = new Rect();
        conditionPaint.getTextBounds(condition, 0, condition.length(), conditionRect);
        int conditionX = 25;
        int conditionY = y + tempRect.height();
        c.drawText(condition, conditionX, conditionY, conditionPaint);


        Paint locationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        locationPaint.setColor(Color.WHITE);
        locationPaint.setStyle(Paint.Style.FILL);
        locationPaint.setTextSize(100);
        locationPaint.setShadowLayer(3f, -2f, 2f, Color.BLACK);

        Rect locationRect = new Rect();

        locationPaint.getTextBounds(location, 0, location.length(), locationRect);
        int locationX = conditionX + conditionRect.width() + 25;
        int locationY = conditionY;
        c.drawText("@" + location, locationX, locationY, locationPaint);

        return newImage;
    }
}
