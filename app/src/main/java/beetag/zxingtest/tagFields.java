package beetag.zxingtest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.SimpleDateFormat;
import android.view.View.OnClickListener;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;
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

        // Datepicker popup
        MyEditTextDatePicker fromDate = new MyEditTextDatePicker(this, R.id.editText10);

        // Timepicker popup
        final EditText timePop = (EditText) findViewById(R.id.editText11);
        timePop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(tagFields.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if ( selectedMinute <= 9 ){
                            timePop.setText( selectedHour + ":0" + selectedMinute);
                        }
                        else {
                            timePop.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
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

                EditText dateSpin = (EditText) findViewById(R.id.editText10);
                String dateValue = dateSpin.getText().toString();

                EditText timeSpin = (EditText) findViewById(R.id.editText11);
                String timeValue = timeSpin.getText().toString();

                updateArray [i][0] = dateValue;
                updateArray [i][1] = timeValue;
                updateArray [i][2] =  bandEValue;
                updateArray [i][3] = beeEValue;
                updateArray [i][4] = rfidValue;

                ((MyApplication)getApplicationContext()).setArray(updateArray);
                int j = i + 1;
                ((MyApplication)getApplicationContext()).setCounter(j);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("keyArray", updateArray);
                saveValue.putExtras(mBundle);
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

                EditText dateSpin = (EditText) findViewById(R.id.editText10);
                String dateValue = dateSpin.getText().toString();

                EditText timeSpin = (EditText) findViewById(R.id.editText11);
                String timeValue = timeSpin.getText().toString();

                updateArray [i][0] = dateValue;
                updateArray [i][1] = timeValue;
                updateArray [i][2] =  bandEValue;
                updateArray [i][3] = beeEValue;
                updateArray [i][4] = rfidValue;

                ((MyApplication)getApplicationContext()).setArray(updateArray);
                int j = i + 1;
                ((MyApplication)getApplicationContext()).setCounter(j);
                startActivity(saveValue);
            }
        });


    }
}
