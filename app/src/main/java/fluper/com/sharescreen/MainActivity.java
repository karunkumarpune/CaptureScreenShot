package fluper.com.sharescreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import fluper.com.sharescreen.gif.GIFView;

/**
 * Created by fluper-pc on 1/2/18.
 */

public class MainActivity extends AppCompatActivity {
    LinearLayout ll_screensort_share;
    Button btn_shares;
    private Bitmap mbitmap;

    private GIFView loader_report;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_screensort_share = (LinearLayout)findViewById(R.id.ll_screensort_share);
        btn_shares = (Button)findViewById(R.id.btn_share);


        loader_report = (GIFView)findViewById(R.id.loader_report);
        loader_report.setImageResource(R.drawable.cols);


        btn_shares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    screenShot(view);
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.setType("image/png");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Perfec10");
                    i.putExtra(Intent.EXTRA_TEXT, "This is good.");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    i.putExtra(Intent.EXTRA_STREAM, getImageUri(getApplicationContext(), mbitmap));
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void screenShot(View view) {
        mbitmap = getBitmapOFRootView(btn_shares);
        //   iv_instagram_share.setImageBitmap(mbitmap);
        createImage(mbitmap);
    }
    public Bitmap getBitmapOFRootView(View v) {
        View rootview = ll_screensort_share;
        rootview.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = rootview.getDrawingCache();
        return bitmap1;
    }


    public void createImage(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File(Environment.getExternalStorageDirectory() + "/capturedscreenandroid.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
