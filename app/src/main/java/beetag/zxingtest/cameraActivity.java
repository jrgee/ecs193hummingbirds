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

/**
 * The cameraActivity class is used to display information passed from the previous camera screen
 */
public class cameraActivity extends AppCompatActivity {
    public Button listView, snapView;
    CharSequence decimalLabel;
    Intent intent = getIntent();
    final String decimalValue = intent.getStringExtra("decimal");

    /**
     ** Initalizes the activity and sets up a listener for the button press in order to move to the
     * tag field screen
     */
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

    /**
     * Sets up actions to be taken upon entering the cameraActivity screen including retrieving the
     * the decimal value of the previous screen and displaying the number on the current screen
     * @param savedInstanceState the data of the current screen
     */
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

    /**
     * Clicking the decimal number will copy the data from the clipboard
     * @param v the current screen view
     */
    public void textViewClick(View v){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(decimalLabel, decimalValue);
        clipboard.setPrimaryClip(clip);
    }

    // Do not need to display thumbnail of image taken

}