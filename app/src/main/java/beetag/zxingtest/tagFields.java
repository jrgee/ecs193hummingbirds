package beetag.zxingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.Application;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.logging.LoggingPermission;

/**
 * The tagFields class controls the aspects of the Tag Field screen
 */
public class tagFields extends AppCompatActivity {

    String[][] stringArray = new String[4][5];
    String[][] updateArray;
    /**
     * Displays the formatted tag fields onto the current screen
     * @param savedInstanceState the data of the current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int i = ((MyApplication)getApplicationContext()).getCounter();
        updateArray = ((MyApplication)getApplicationContext()).getArray();
        Log.d("myTag", ""+i);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_fields);

        //Send to Table page
        Button tableButton = (Button) findViewById(R.id.button26);
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveValue = new Intent(tagFields.this, tableActivity.class);
                EditText bandText = (EditText) findViewById(R.id.editText);
                String bandEValue = bandText.getText().toString();
                EditText beeText = (EditText) findViewById(R.id.editText2);
                String beeEValue = beeText.getText().toString();
                EditText rfidText = (EditText) findViewById(R.id.editText3);
                String rfidValue = rfidText.getText().toString();

                Spinner ageSpin = (Spinner) findViewById(R.id.spinner);
                String ageValue = ageSpin.getSelectedItem().toString();

                Spinner sexSpin = (Spinner) findViewById(R.id.spinner26);
                String sexValue = sexSpin.getSelectedItem().toString();

                updateArray [i][0] =  bandEValue;
                updateArray [i][1] = beeEValue;
                updateArray [i][2] = rfidValue;
                updateArray [i][3] = ageValue;
                updateArray [i][4] = sexValue;

                /*stringArray [i][0] =  bandEValue;
                stringArray [i][1] = beeEValue;
                stringArray [i][2] = rfidValue;
                stringArray [i][3] = ageValue;
                stringArray [i][4] = sexValue;*/

                //((MyApplication)getApplicationContext()).setArray(updateArray);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("keyArray", updateArray);
                saveValue.putExtras(mBundle);
                //saveValue.putExtra("bandText",bandEValue);
                //saveValue.putExtra("beeText",beeEValue);
                /*saveValue.putExtra("beeArray",stringArray);*/
                //saveValue.putExtra("value",i);
                startActivity(saveValue);
            }
        });

        Button mainButton = (Button) findViewById(R.id.button27);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveValue = new Intent(tagFields.this, MainActivity.class);
                EditText bandText = (EditText) findViewById(R.id.editText);
                String bandEValue = bandText.getText().toString();
                EditText beeText = (EditText) findViewById(R.id.editText2);
                String beeEValue = beeText.getText().toString();
                EditText rfidText = (EditText) findViewById(R.id.editText3);
                String rfidValue = rfidText.getText().toString();

                Spinner ageSpin = (Spinner) findViewById(R.id.spinner);
                String ageValue = ageSpin.getSelectedItem().toString();

                Spinner sexSpin = (Spinner) findViewById(R.id.spinner26);
                String sexValue = sexSpin.getSelectedItem().toString();

                updateArray [i][0] =  bandEValue;
                updateArray [i][1] = beeEValue;
                updateArray [i][2] = rfidValue;
                updateArray [i][3] = ageValue;
                updateArray [i][4] = sexValue;

                ((MyApplication)getApplicationContext()).setArray(updateArray);
                int j = i + 1;
                ((MyApplication)getApplicationContext()).setCounter(j);
                startActivity(saveValue);
            }
        });


    }
}
