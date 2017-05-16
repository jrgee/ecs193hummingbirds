package beetag.zxingtest.ocrreader;

import android.content.Context;
import android.content.Intent;
//import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.net.Uri;
import android.widget.Toast;
import beetag.zxingtest.R;
import beetag.zxingtest.tagFields;
//import android.os.Environment;

//import org.w3c.dom.Text;

/**
 * The cameraActivity class is used to display information passed from the previous activity
 */
public class cameraActivityRfid extends AppCompatActivity {
    public Button listView, copyView, webView;
    //public TextView snapView;

    /**
     * Sets up actions to be taken upon entering the cameraActivity screen including retrieving the
     * the decimal value of the previous screen and displaying the number on the current screen
     * @param savedInstanceState the data of the current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_rfid);
        TextView rfidStr = (TextView) findViewById(R.id.RfidText);

        // Receive BEEtag data stored in previous activity
        Intent intent = getIntent();
        //final String bin = intent.getStringExtra("binary"); //full binary of BEEtag
        final String rfid = intent.getStringExtra("rfid");

        rfidStr.setText(rfid);

        // Opens next page with Auxiliary Marker button press
        listView = (Button) findViewById(R.id.markerButton);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upon button press, go to Auxiliary Markers page
                // TODO: send tag number to proper fields in markers page
                Intent saveValue = new Intent(cameraActivityRfid.this, tagFields.class);
                startActivity(saveValue);
            }
        });

        // Copies the decimal value onto clipboard
        copyView = (Button) findViewById(R.id.copyButton);
        copyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", rfid);
                clipboard.setPrimaryClip(clip);

                //display message to user
                Toast toast = Toast.makeText(getApplicationContext(), "RFID copied to clipboard", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        webView = (Button) findViewById(R.id.webButton);
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open web interface in browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://data.hummingbirdhealth.org/Main.aspx"));
                startActivity(browserIntent);
            }
        });
    }
/*
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
*/
}