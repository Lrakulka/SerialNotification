package parser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.gencha.alarmexample.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by asd on 13/12/15.
 */
public class LoadImages  extends AsyncTask<Void, Void, Void> {
    private ImageView imageView;
    private String url;
    private Bitmap bitmap;

    public LoadImages(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (bitmap == null) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }
}

