package beetag.zxingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

//everything in this file is hacked together from various sources on Stack Overflow
//this is intended as a proof of concept rather than any semblance of final code


public class cameraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        TextView decStr = (TextView) findViewById(R.id.decText);
        Intent intent = getIntent();
        String decimalValue = intent.getStringExtra("decimal");
        decStr.setText(decimalValue);

    }

    public void showInfo(){
        //Intent intent = getIntent()
        //Bundle extras = intent.getExtras();
        //Bitmap bitImage = extras.getParcelable("bitmap");

        //ImageView imageview = (ImageView) findViewById(R.id.imageView);
        //imageview.setImageBitmap(bitImage);
    }
}