package beetag.zxingtest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class tableActivity extends AppCompatActivity {

    //int row = ((MyApplication)getApplicationContext()).getCounter();
    /*Spinner captureSpin = (Spinner) findViewById(R.id.spinner15);
    String captureValue = captureSpin.getSelectedItem().toString();

    Spinner dateSpin = (Spinner) findViewById(R.id.spinner16);
    String dateValue = dateSpin.getSelectedItem().toString();

    Spinner timeSpin = (Spinner) findViewById(R.id.spinner17);
    String timeValue = timeSpin.getSelectedItem().toString();

    Spinner tailSpin = (Spinner) findViewById(R.id.spinner18);
    String tailValue = tailSpin.getSelectedItem().toString();

    Spinner bandSpin = (Spinner) findViewById(R.id.spinner19);
    String bandValue = bandSpin.getSelectedItem().toString();

    Spinner shortSpin = (Spinner) findViewById(R.id.spinner20);
    String shortValue = shortSpin.getSelectedItem().toString();*/

    /*EditText shortText = (EditText) findViewById(R.id.editText7);
    String shortEValue = shortText.getText().toString();*/

    /*EditText bandText = (EditText) findViewById(R.id.editText);
    String bandEValue = bandText.getText().toString();*/

    /*EditText beeText = (EditText) findViewById(R.id.editText2);
    String beeEValue = beeText.getText().toString();

    EditText rfidText = (EditText) findViewById(R.id.editText3);
    String rfidValue = rfidText.getText().toString();*/

    /*Spinner ageSpin = (Spinner) findViewById(R.id.spinner);
    String ageValue = ageSpin.getSelectedItem().toString();

    Spinner sexSpin = (Spinner) findViewById(R.id.spinner26);
    String sexValue = sexSpin.getSelectedItem().toString();

    Spinner speciesSpin = (Spinner) findViewById(R.id.spinner2);
    String speciesValue = speciesSpin.getSelectedItem().toString();

    Spinner poxSpin = (Spinner) findViewById(R.id.spinner3);
    String poxValue = poxSpin.getSelectedItem().toString();*/

    public void init() {

        int row = ((MyApplication)getApplicationContext()).getCounter();
        String[][] arrayReceived=null;
        Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("keyArray");
        if(objectArray!=null){
            arrayReceived = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                arrayReceived[i]=(String[]) objectArray[i];
            }
        }



        //String bandString = extras.getString("bandText");
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Time ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Band ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" BEETag ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" RFID ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);

        for (int counter = 0; counter <= row; counter ++) {
            String dateString = arrayReceived[counter][0];
            String timeString = arrayReceived[counter][1];
            String bandString = arrayReceived[counter][2];
            String beeString = arrayReceived[counter][3];
            String rfidString = arrayReceived[counter][4];

            //String[] ageSplit = ageString.split(" ");
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(dateString);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(timeString);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(bandString);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(beeString);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(rfidString);
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);
            stk.addView(tbrow);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_table);

        init();
    }
}
