package beetag.zxingtest;

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
//import android.os.Environment;

//import org.w3c.dom.Text;

/**
 * The cameraActivity class is used to display information passed from the previous activity
 */
public class cameraActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_camera);
        TextView decStr = (TextView) findViewById(R.id.decText);

        // Receive BEEtag data stored in previous activity
        Intent intent = getIntent();
        final String bin = intent.getStringExtra("binary"); //full binary of BEEtag
        final String decimal = intent.getStringExtra("decimal");

        //draw tag scanned visually to user
        //tag starts as all black, so only squares that should be white are replaced
        final Drawable white = ContextCompat.getDrawable(getApplicationContext(), R.drawable.white);
        TableLayout tblLayout = (TableLayout)findViewById(R.id.tagTable);
        for(int i=0;i<5;i++)
        {
            TableRow row = (TableRow)tblLayout.getChildAt(i);
            for(int j=0;j<5;j++){
                if(bin.charAt(5*i+j) == '1') { //if white square
                    ImageView square = (ImageView) row.getChildAt(j);
                    square.setImageDrawable(white);
                }
            }
        }

        decStr.setText(decimal);

        // Opens next page with Auxiliary Marker button press
        listView = (Button) findViewById(R.id.markerButton);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upon button press, go to Auxiliary Markers page
                Intent saveValue = new Intent(cameraActivity.this, tagFields.class);
                //CONFLICT RESOLVED
                saveValue.putExtra("beetag", decimal);

                startActivity(saveValue);
            }
        });

        // Copies the decimal value onto clipboard
        copyView = (Button) findViewById(R.id.copyButton);
        copyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", decimal);
                clipboard.setPrimaryClip(clip);

                //display message to user
                Toast toast = Toast.makeText(getApplicationContext(), "ID copied to clipboard", Toast.LENGTH_SHORT);
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
}
