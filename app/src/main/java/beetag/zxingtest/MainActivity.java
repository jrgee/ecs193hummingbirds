package beetag.zxingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Environment;

import com.google.zxing.common.*;
import com.google.zxing.*;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.GridSampler;

import java.io.File;

/**
 * The MainActivity class controls actions of the main screen class of the Hummingbird App
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Sets up actions to be taken upon entering the main screen including button setup to
     * transition to the camera screen
     * @param savedInstanceState the data of the current screen
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //open built-in Android camera and save temporary image
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "scan.jpg");
                Uri outputFileUri = Uri.fromFile(file);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    /**
     * Retrieves image data from the camera and decodes bit patterns into a corresponding number and
     * passes the number to the next screen
     * @param requestCode the requestCode passed to the function must be 1 in order to decode image
     * @param resultCode the resultCode passed to the function must be void and successful
     * @param data the image data taken by the camera
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){ //on picture successfully taken
            //get TextViews on main screen (temporary)
            //TextView param = (TextView) findViewById(R.id.param_string);
            //TextView results = (TextView) findViewById(R.id.results_string);
            Decoder decoder = new Decoder();


            String decString = "Error";

            //get image to bitmap
            File imgFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "scan.jpg");
            Bitmap bMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            decString = decoder.decodedTag(bMap);

            //Pass BeeTag info to next screen
            Intent saveIntent= new Intent(MainActivity.this, cameraActivity.class);
            saveIntent.putExtra("decimal", decString);
            startActivity(saveIntent);
        }

    }

}