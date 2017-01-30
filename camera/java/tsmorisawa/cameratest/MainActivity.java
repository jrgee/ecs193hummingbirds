package tsmorisawa.cameratest;

import android.graphics.Bitmap;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


//everything in this file is hacked together from various sources on Stack Overflow
//this is intended as a proof of concept rather than any semblance of final code

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = (Button) findViewById(R.id.scanbutton);
        scanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Intent saveIntent= new Intent(MainActivity.this, PictureActivity.class);
            saveIntent.putExtra("bitmap", image);
            startActivity(saveIntent);
        }
    }
}
