package tsmorisawa.cameratest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;

//everything in this file is hacked together from various sources on Stack Overflow
//this is intended as a proof of concept rather than any semblance of final code

public class PictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Button nextButton = (Button) findViewById(R.id.nextbutton);
        nextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent nextIntent= new Intent(PictureActivity.this, CaptureInfo.class);
                startActivity(nextIntent);
            }
        });
        showImage();
    }

    public void showImage(){
        Bitmap bitImage = getIntent().getParcelableExtra("bitmap");
        ImageView imageview = (ImageView) findViewById(R.id.imageView);
        imageview.setImageBitmap(bitImage);
    }
}
