package beetag.zxingtest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.TextView;

import com.google.zxing.common.*;
import com.google.zxing.*;
import com.google.zxing.common.GridSampler;

import java.io.File;

import beetag.zxingtest.ocrreader.OcrCaptureActivity;

/**
 *Controls actions of the main screen class of the Hummingbird App
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Sets up actions to be taken upon entering the main screen including button setup to
     * transition to the camera screen
     * @param savedInstanceState the data of the current screen
     */
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get camera permissions
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            //createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }

        //Get intrn permissions


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView debug_info = (TextView) findViewById(R.id.debug_info);
        Intent intent = getIntent();
        final String info= intent.getStringExtra("debugInfo");
        debug_info.setText(info);


        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //open built-in Android camera and save temporary image
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "scan.jpg");
                Uri outputFileUri = FileProvider.getUriForFile(MainActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, 1);
            }
        });

        //draw tag
        Button drawButton = (Button) findViewById(R.id.draw_button);
        drawButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent drawIntent = new Intent(MainActivity.this, drawActivity.class);
                startActivity(drawIntent);
            }
        });

        //Send to Recapture page
        Button recapButton = (Button) findViewById(R.id.recapture_button);
        recapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveValue = new Intent(MainActivity.this, tagFields.class);
                startActivity(saveValue);
            }
        });

        //Send to Recapture page
        Button tableButton = (Button) findViewById(R.id.table_button);
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tableIntent = new Intent(MainActivity.this, tableActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("keyArray", null);
                tableIntent.putExtras(mBundle);
                startActivity(tableIntent);
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
        TextView debugInfo = (TextView) findViewById(R.id.debug_info);
        int tag_found = 0, INIT_SIZE = 10, x = 0, y = 0;
        if (requestCode == 1 && resultCode == RESULT_OK) { //on picture successfully taken
            //use the following line instead of the "file" related lines to use thumbnail instead of full image
            //Bitmap thumbMap = (Bitmap) data.getExtras().get("data");

            //get image to bitmap
            File imgFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "scan.jpg");
            Bitmap bMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
            //copy pixel data from the Bitmap into the 'intArray' array
            bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

            //create LuminanceSource and run through Binarizer to get BinaryBitmap
            LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            BitMatrix reader;
            BitMatrix bits = new BitMatrix(5,5);

            try {
                //find code in picture and return corner locations
                x = bitmap.getBlackMatrix().getWidth()/2;
                y = bitmap.getBlackMatrix().getHeight()/2;
                ModDetector detector = new ModDetector(bitmap.getBlackMatrix(), INIT_SIZE, x ,y);
                ResultPoint[] corners = detector.detect();

                //print corner locations to TextView (for debugging)
                //param.setText(corners[0].toString() + " " + corners[2].toString() + " " +
                //corners[3].toString() + " " + corners[1].toString());

                //convert code into its binary representation stored in BitMatrix
                GridSampler sampler = GridSampler.getInstance();
                int res = 7;
                reader = sampler.sampleGrid(bitmap.getBlackMatrix(), res,res,
                        0.5f, 0.5f, res - 0.5f, 0.5f, res - 0.5f, res - 0.5f, 0.5f, res - 0.5f,
                        corners[0].getX(), corners[0].getY(), corners[2].getX(), corners[2].getY(),
                        corners[3].getX(), corners[3].getY(), corners[1].getX(), corners[1].getY());

                //print converted BitMatrix (for debugging)
                //results.setText(bits.toString());
                //Log.d("Full bits", reader.toString());
                Log.d("First Pass", x + ", " + y);
                for(int j = 1; j < 6; j++){
                    for(int i = 1; i < 6; i++){
                        if(reader.get(i,j)){
                            //Log.d("debug ", i + " " + j);
                            bits.set((i-1),(j-1));
                        }
                    }
                }
                //Log.d("Partial bits", bits.toString());
            } catch (Exception e) {
                //couldn't find code in image
                Log.d("no code found", x + ", " + y);
                e.printStackTrace();
                tag_found = -1;
                //return;
            }
            if (tag_found == -1) {
                debugInfo.setText("No BEEtag found");
                return;
            }
            //NOTE: "row" and "column" are used below referring to the original orientation of the tag
            //The BEEtag specification uses "column" to refer to rows in the original orientation when mentioning the parity format
            //Also note that BEEtag uses white for 1 and 0 for black while ZXing uses true for black and false for white.
            int dec = -1;
            for (int i = 0; i < 4; i++) {
                dec = decode(bits);
                if (dec != -1) {
                    //Pass BeeTag info to next screen
                    debugInfo.setText("");
                    Intent saveIntent= new Intent(MainActivity.this, cameraActivity.class);
                    saveIntent.putExtra("binary", matToBinString(bits));
                    saveIntent.putExtra("decimal", Integer.toString(dec));
                    startActivity(saveIntent);
                    debugInfo.setText("");
                    break;
                } else {
                    bits = rotate(bits, 5);
                    //Log.d("bits", bits.toString());
                }
            }
            if (dec == -1)
                debugInfo.setText("BEEtag invalid");
        }
    }


    /**
     * Returns a copy of the provided BitMatrix rotated counter-clockwise by 90 degrees
     * @param bits the BitMatrix to rotate
     * @param size the size of the BitMatrix (always 5)
     */
    static BitMatrix rotate(BitMatrix bits, int size){
        BitMatrix x = new BitMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(bits.get(size - j - 1, i))
                    x.set(i, j);
            }
        }
        return x;
    }

    /**
     * Returns the decimal value of the provided BitMatrix or -1 if it is invalid
     * @param bits the BitMatrix to decode
     */
    static int decode(BitMatrix bits){
        int dec = 0; //stores decimal number of tag
        int[] par = new int[5]; //stores expected parity results

        //convert BitMatrix into decimal representation and set column parity bits
        for(int i=0; i<3; i++){ //for first 3 columns
            par[i] = 0; //initialize to even (0)

            for(int j=0; j<5; j++){ //for each row
                if(!bits.get(i, j)) { //if white square (representing 1)
                    dec += Math.pow(2, 14 - 5 * i - j);
                    par[i] = 1 - par[i]; //flip parity for each white
                }
            }
        }

        //set last two parity bits
        par[3] = 0; //initialize to even (0)
        for(int j=0; j<3; j++){ //for first 3 rows
            for(int i=0; i<3; i++){ //for first 3 columns
                if(!bits.get(i, j)) { //if white square (representing 1)
                    par[3] = 1 - par[3]; //flip parity for each white
                }
            }
        }

        par[4] = 0; //initialize to even (0)
        for(int j=3; j<5; j++){ //for last 2 rows
            for(int i=0; i<3; i++){ //for first 3 columns
                if(!bits.get(i, j)) { //if white square (representing 1)
                    par[4] = 1 - par[4]; //flip parity for each white
                }
            }
        }
      
        //check parity for 4th column
        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(3, j) && par[j] == 0) || (bits.get(3, j) && par[j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 1 failed.");
                return -1;
            }
        }

        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(4, j) && par[4-j] == 0) || (bits.get(4, j) && par[4-j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 2 failed.");
                return -1;
            }
        }

        return dec;
    }

    /**
     * Returns the binary value of the provided BitMatrix as a string
     * @param bits the BitMatrix to rotate
     */
    static String matToBinString(BitMatrix bits){
        String str = "";
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++)
                if(bits.get(j,i))
                    str += "0";
                else
                    str += "1";

        return str;
    }

    private void requestCameraPermission() {
        Log.w("Main", "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

       /* Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
                */
    }
}
