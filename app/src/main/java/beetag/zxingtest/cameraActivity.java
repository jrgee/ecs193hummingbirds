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
import android.os.Environment;

import org.w3c.dom.Text;

/**
 * The cameraActivity class is used to display information passed from the previous activity
 */
public class cameraActivity extends AppCompatActivity {
    public Button listView;
    public TextView snapView;

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

        // Receive BEEtag data stored in previous activity
        Intent intent = getIntent();
        final String decimal= intent.getStringExtra("decimal");


        // Opens next page with Auxillary Marker button press
        listView = (Button) findViewById(R.id.button2);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upon button press, go to Auxillary Markers page
                Intent saveValue = new Intent(cameraActivity.this, tagFields.class);
                startActivity(saveValue);
            }
        });
    }

    public void textViewClick(View v){

        // Sets up Copy button process
        snapView = (TextView) findViewById(R.id.decText);
        final String decimalValue = snapView.getText().toString();
        snapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Copies the decimal value onto clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", decimalValue);
                clipboard.setPrimaryClip(clip);
            }
        });

    }

}