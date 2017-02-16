package beetag.zxingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.common.*;
import com.google.zxing.*;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.GridSampler;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            TextView param = (TextView) findViewById(R.id.param_string);
            TextView results = (TextView) findViewById(R.id.results_string);

            Bitmap bMap = (Bitmap) data.getExtras().get("data");

            int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
            //copy pixel data from the Bitmap into the 'intArray' array
            bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

            LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(),intArray);

            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try
            {
                WhiteRectangleDetector detector = new WhiteRectangleDetector(bitmap.getBlackMatrix());
                ResultPoint[] corners = detector.detect();
                GridSampler sampler = GridSampler.getInstance();
                BitMatrix bits = sampler.sampleGrid(bitmap.getBlackMatrix(), 5, 5,
                        0.5f, 0.5f, 5-0.5f, 0.5f, 5-0.5f, 5-0.5f, 0.5f, 5-0.5f,
                        corners[0].getX(), corners[0].getY(), corners[2].getX(), corners[2].getY(),
                        corners[3].getX(), corners[3].getY(), corners[1].getX(), corners[1].getY());

                param.setText(corners[0].toString() + " " + corners[2].toString() + " " +
                        corners[3].toString() + " " + corners[1].toString());
                results.setText(bits.toString());
            }
            catch(Exception e)
            {
                results.setText("Error!");
                param.setText("Error!");
                e.printStackTrace();
            }

            //Intent saveIntent= new Intent(main_screen.this, cameraTest.class);
            //saveIntent.putExtra("bitmap", image);
            //startActivity(saveIntent);
        }
    }

}