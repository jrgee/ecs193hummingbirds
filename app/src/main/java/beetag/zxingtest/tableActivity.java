package beetag.zxingtest;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class tableActivity extends AppCompatActivity {

    //int row = ((MyApplication)getApplicationContext()).getCounter();

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


        // Create column headers for Table
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tvCount = new TextView(this);
        /*tvCount.setText(" # ");
        tvCount.setTextColor(Color.WHITE);
        tbrow0.addView(tvCount);*/
        TextView tv0 = new TextView(this);
        tv0.setText("     Date     ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("     Time     ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("     Band     ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("     BEETag     ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText("     RFID     ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);

        /*
        // Sort by Date
        int dateYear, nextYear, nextCounter;
        int dateMonth, nextMonth, dateDay, nextDay;
        for (int counter = 0; counter < row; counter ++) {
            String dateString = arrayReceived[counter][0];
            nextCounter = counter + 1;
            String nextDate = arrayReceived[nextCounter][0];
            String[] dateSplit = dateString.split("/");
            String[] nextSplit = nextDate.split("/");
            dateYear = Integer.parseInt(dateSplit[2]);
            nextYear = Integer.parseInt(nextSplit[2]);
            dateMonth = Integer.parseInt(dateSplit[0]);
            nextMonth = Integer.parseInt(nextSplit[0]);
            dateDay = Integer.parseInt(dateSplit[1]);
            nextDay = Integer.parseInt(nextSplit[1]);

            if (dateYear > nextYear){
                String [] tempStore = arrayReceived[counter];
                arrayReceived[counter] = arrayReceived[nextCounter];
                arrayReceived[nextCounter] = tempStore;
            }

            else if (dateYear == nextYear){
                if (dateMonth > nextMonth){
                    String [] tempStore = arrayReceived[counter];
                    arrayReceived[counter] = arrayReceived[nextCounter];
                    arrayReceived[nextCounter] = tempStore;
                }
            }

            else if ( dateYear == nextYear && dateMonth == nextMonth){
                if (dateDay > nextDay){
                    String [] tempStore = arrayReceived[counter];
                    arrayReceived[counter] = arrayReceived[nextCounter];
                    arrayReceived[nextCounter] = tempStore;
                }
            }
        }

*/
        // Add recapture data onto Table
        for (int counter = 0; counter < row; counter ++) {
            String dateString = arrayReceived[counter][4];
            String timeString = arrayReceived[counter][5];
            String bandString = arrayReceived[counter][10];
            String beeString = arrayReceived[counter][11];
            String rfidString = arrayReceived[counter][12];

            //String[] ageSplit = ageString.split(" ");
            TableRow tbrow1 = new TableRow(this);
            /*TextView t0v = new TextView(this);
            int realCount = counter + 1;
            t0v.setText(""+realCount);
            t0v.setTextColor(Color.WHITE);
            t0v.setGravity(Gravity.CENTER);
            tbrow.addView(t0v);*/
            TextView t1v = new TextView(this);
            t1v.setText(dateString);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow1.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(timeString);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow1.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(bandString);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow1.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(beeString);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow1.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(rfidString);
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow1.addView(t5v);
            stk.addView(tbrow1);
        }

        // Create spacing between last row and button
        TableRow tbrow = new TableRow(this);
        //TableRow tbrow2 = new TableRow(this);
            /*TextView t0v = new TextView(this);
            int realCount = counter + 1;
            t0v.setText(""+realCount);
            t0v.setTextColor(Color.WHITE);
            t0v.setGravity(Gravity.CENTER);
            tbrow.addView(t0v);*/
        TextView t1vv = new TextView(this);
        t1vv.setText("");
        t1vv.setTextColor(Color.WHITE);
        t1vv.setGravity(Gravity.CENTER);
        tbrow.addView(t1vv);
        TextView t2vv = new TextView(this);
        t2vv.setText("");
        t2vv.setTextColor(Color.WHITE);
        t2vv.setGravity(Gravity.CENTER);
        tbrow.addView(t2vv);
        TextView t3vv = new TextView(this);
        t3vv.setText("");
        t3vv.setTextColor(Color.WHITE);
        t3vv.setGravity(Gravity.CENTER);
        tbrow.addView(t3vv);
        TextView t4vv = new TextView(this);
        t4vv.setText("");
        t4vv.setTextColor(Color.WHITE);
        t4vv.setGravity(Gravity.CENTER);
        tbrow.addView(t4vv);
        TextView t5vv = new TextView(this);
        t5vv.setText("");
        t5vv.setTextColor(Color.WHITE);
        t5vv.setGravity(Gravity.CENTER);
        tbrow.addView(t5vv);
        stk.addView(tbrow);

        // Add Send button to last row of Table
        Button btn = new Button(this);
        btn.setText("Send");
        btn.setId(0);
        TableRow tbrow2 = new TableRow(this);
        TableLayout.LayoutParams layoutRow = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);
        tbrow2.setLayoutParams(layoutRow);
        TableRow.LayoutParams layoutHistory = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
        btn.setLayoutParams(layoutHistory);
        btn.setGravity(Gravity.CENTER);
        tbrow2.setGravity(Gravity.CENTER);
        tbrow2.addView(btn);
        stk.addView(tbrow2);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_table);

        init();

        //Send data to Database
        Button recapButton = (Button) findViewById(0);
        recapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = ((MyApplication)getApplicationContext()).getCounter();
                String[][] sendArray = ((MyApplication) getApplicationContext()).getArray();
                for ( int currRow = 0; currRow < counter; currRow ++) {
                    new NetworkOp().execute(sendArray[currRow]);
                }
            }
        });
    }
}
