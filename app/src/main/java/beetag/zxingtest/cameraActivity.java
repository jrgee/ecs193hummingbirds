package beetag.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.content.ClipboardManager;
import android.content.ClipData;


public class cameraActivity extends AppCompatActivity {
    public Button listView, snapView;
    CharSequence decimalLabel;
    Intent intent = getIntent();
    final String decimalValue = intent.getStringExtra("decimal");

    public void init() {

        // Receive BEEtag data stored in previous activity
        Intent intent = getIntent();
        final String decimalValue = intent.getStringExtra("decimal");

        // Opens next page with Auxillary Marker button press
        listView = (Button) findViewById(R.id.button2);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upon button press, go to Auxillary Markers page
                Intent toy = new Intent(cameraActivity.this, tagFields.class);
                startActivity(toy);
            }
        });

        // Sets up Copy button process
        /*snapView = (Button) findViewById(R.id.button3);
        snapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Copies the decimal value onto clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(decimalLabel, decimalValue);
                clipboard.setPrimaryClip(clip);
            }
        });*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        TextView decStr = (TextView) findViewById(R.id.decText);

        // Receive BEEtag data stored in previous activity and display data on screen
        Intent intent = getIntent();
        final String decimalValue = intent.getStringExtra("decimal");
        decStr.setText(decimalValue);
    }

    public void modelTextViewClick(View v){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(decimalLabel, decimalValue);
        clipboard.setPrimaryClip(clip);
    }

    // Do not need to display thumbnail of image taken

    /*
    public void showInfo(){
        //Intent intent = getIntent()
        //Bundle extras = intent.getExtras();
        //Bitmap bitImage = extras.getParcelable("bitmap");

        //ImageView imageview = (ImageView) findViewById(R.id.imageView);
        //imageview.setImageBitmap(bitImage);
    }*/
}