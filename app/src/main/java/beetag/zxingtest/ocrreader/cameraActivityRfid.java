package beetag.zxingtest.ocrreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import beetag.zxingtest.R;

//import org.w3c.dom.Text;

/**
 * The cameraActivity class is used to display information passed from the previous activity
 */
public class cameraActivityRfid extends AppCompatActivity {
    /**
     * Sets up actions to be taken upon entering the cameraActivity screen including retrieving the
     * the decimal value of the previous screen and displaying the number on the current screen
     * @param savedInstanceState the data of the current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_rfid);
        TextView rfidStr = (EditText) findViewById(R.id.codetext);

        // Receive BEEtag data stored in previous activity
        Intent intent = getIntent();
        final String rfid = intent.getStringExtra("rfid");

        rfidStr.setText(rfid);

        // Opens next page with Auxiliary Marker button press
        Button listView = (Button) findViewById(R.id.markerButton);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText rfidStr = (EditText) findViewById(R.id.codetext);
                String result = rfidStr.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}