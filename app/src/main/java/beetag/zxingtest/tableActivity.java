package beetag.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.StrictMode;
import android.widget.Toast;

public class tableActivity extends AppCompatActivity {

    public void init() {
        int row = ((MyApplication)getApplicationContext()).getCounter();

        //Intent intent = getIntent();
        //final String fromMain = intent.getStringExtra("fromMain");

        String[][] arrayReceived = null;
        Object[] objectArray = ((MyApplication)getApplicationContext()).getArray();
        if(objectArray != null){
            arrayReceived = new String[objectArray.length][];
            for(int i=0; i<objectArray.length; i++){
                arrayReceived[i] = (String[])objectArray[i];
            }
        }


        // Create column headers for Table
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        String headAr[] = {" Delete ", "     Date     ", "     Time     ", "     Band     ", "     BEETag     ", "     RFID     "};
        int lengthAr[] = new int[6];
        for(int i=0; i<6; i++) {
            TextView tv0 = new TextView(this);
            tv0.setText(headAr[i]);
            tv0.setTextSize(18);
            tv0.setTextColor(Color.WHITE);
            tbrow0.addView(tv0);
            lengthAr[i] = tv0.getWidth();
        }
        stk.addView(tbrow0);

        // Add recapture data onto Table
        if(arrayReceived != null) {
            for (int counter = 0; counter < row; counter++) {
                String dateString = arrayReceived[counter][4];
                String timeString = arrayReceived[counter][5];
                String bandString = arrayReceived[counter][10];
                String beeString = arrayReceived[counter][11];
                String rfidString = arrayReceived[counter][12];
                String rowAr[] = {dateString, timeString, bandString, beeString, rfidString};

                TableRow tbrow1 = new TableRow(this);

                Button btn = new Button(this);
                btn.setText("Delete");
                btn.setId(counter+1);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteRow(v.getId()-1);
                    }
                });
                tbrow1.addView(btn);

                for(int i=1; i<6; i++){
                    TextView t1v = new TextView(this);
                    t1v.setText(rowAr[i-1]);
                    t1v.setTextSize(16);
                    t1v.setTextColor(Color.WHITE);
                    t1v.setGravity(Gravity.CENTER);

                    //cut TextView short if contents exceed length
                    t1v.setEllipsize(TextUtils.TruncateAt.END);
                    t1v.setSingleLine();
                    t1v.setHorizontallyScrolling(false);
                    t1v.setMaxWidth(lengthAr[i]);

                    tbrow1.addView(t1v);
                }

                stk.addView(tbrow1);
            }
        }

        // Create spacing between last row and button
        TableRow spacer = new TableRow(this);

        for(int i=0; i<5; i++) {
            TextView spacev = new TextView(this);
            spacev.setText("");
            spacer.addView(spacev);
        }
        stk.addView(spacer);

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        init();

        //Send data to Database
        Button recapButton = (Button) findViewById(0);
        recapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Context context = getApplicationContext();
            TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            int counter = ((MyApplication)context).getCounter();
            if(counter > 0) {
                String[][] sendArray = ((MyApplication) context).getArray();

                //send in reverse order (bottom to top)
                for (int currRow = counter-1; currRow >= 0; currRow--) {
                    sendArray[currRow][19] = Integer.toString(currRow + 1);
                    new NetworkOp(context, stk).execute(sendArray[currRow]);
                }
            }
            else
            {
                Toast toast = Toast.makeText(context, "No data to send", Toast.LENGTH_SHORT);
                toast.show();
            }
            }
        });

        //Go back to home page
        Button homeButton = (Button) findViewById(R.id.table_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent saveValue = new Intent(tableActivity.this, MainActivity.class);
                //startActivity(saveValue);
                finish();
            }
        });

    }

    private void deleteRow(int row)
    {
        ((MyApplication)getApplicationContext()).deleteRow(row);
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.removeViewAt(row + 1);
    }
}
