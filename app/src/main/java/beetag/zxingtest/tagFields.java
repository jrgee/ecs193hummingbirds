package beetag.zxingtest;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * The tagFields class controls the aspects of the Tag Field screen
 */
public class tagFields extends AppCompatActivity {

    //String[][] stringArray = new String[4][5];
    String[][] updateArray;
    /**
     * Displays the formatted tag fields onto the current screen
     * @param savedInstanceState the data of the current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int i = ((MyApplication)getApplicationContext()).getCounter();
        updateArray = ((MyApplication)getApplicationContext()).getArray();
        //Log.d("myTag", ""+i);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_fields);

        Intent intent = getIntent();
        final String beetag = intent.getStringExtra("beetag");
        EditText beeText = (EditText) findViewById(R.id.beeText);
        beeText.setText(beetag);


        // OCR button
        Button ocrButton = (Button) findViewById(R.id.ocr_button);
        ocrButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                int rc = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                if(rc == PackageManager.PERMISSION_GRANTED) {
                    Intent OcrCaptureActivity = new Intent(tagFields.this, beetag.zxingtest.ocrreader.OcrCaptureActivity.class);
                    startActivityForResult(OcrCaptureActivity, 2);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Camera Permissions Denied", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        // Datepicker popup
        MyEditTextDatePicker fromDate = new MyEditTextDatePicker(this, R.id.dateText);

        // Timepicker popup
        final EditText timePop = (EditText) findViewById(R.id.timeText);
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
        Button tableButton = (Button) findViewById(R.id.tableButton);
        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveValue = new Intent(tagFields.this, tableActivity.class);
                EditText bandText = (EditText) findViewById(R.id.bandNum);
                String bandEValue = bandText.getText().toString();

                EditText beeText = (EditText) findViewById(R.id.beeText);
                String beeEValue = beeText.getText().toString();

                EditText rfidText = (EditText) findViewById(R.id.rfidText);
                String rfidValue = rfidText.getText().toString();

                Spinner ageSpin = (Spinner) findViewById(R.id.ageSpin);
                String ageValue = ageSpin.getSelectedItem().toString();

                Spinner sexSpin = (Spinner) findViewById(R.id.sexSpin);
                String sexValue = sexSpin.getSelectedItem().toString();

                Spinner recorderSpin = (Spinner) findViewById(R.id.recorderSpin);
                String recorder = recorderSpin.getSelectedItem().toString();

                Spinner locationSpin = (Spinner) findViewById(R.id.locationSpin);
                String location = locationSpin.getSelectedItem().toString();

                Spinner lifeSpin = (Spinner) findViewById(R.id.lifeSpin);
                String lifeValue = lifeSpin.getSelectedItem().toString();

                Spinner methodSpin = (Spinner) findViewById(R.id.methodSpin);
                String method = methodSpin.getSelectedItem().toString();

                Spinner tailSpin = (Spinner) findViewById(R.id.tailSpin);
                String tailBase = tailSpin.getSelectedItem().toString();

                Spinner bandSpin = (Spinner) findViewById(R.id.bandSpin);
                String bandCode = bandSpin.getSelectedItem().toString();

                Spinner shortSpin = (Spinner) findViewById(R.id.shortBandLet);
                String shortBL = shortSpin.getSelectedItem().toString();

                Spinner speciesSpin = (Spinner) findViewById(R.id.speciesSpin);
                String species = speciesSpin.getSelectedItem().toString();

                Spinner poxSpin = (Spinner) findViewById(R.id.poxSpin);
                String pox = poxSpin.getSelectedItem().toString();

                EditText shortNumSpin = (EditText) findViewById(R.id.shortBandNum);
                String shortBN = shortNumSpin.getText().toString();

                EditText dateText = (EditText) findViewById(R.id.dateText);
                String dateValue = dateText.getText().toString();

                EditText timeText = (EditText) findViewById(R.id.timeText);
                String timeValue = timeText.getText().toString();

                updateArray [i][0] = recorder;
                updateArray [i][1] = location;
                updateArray [i][2] =  lifeValue;
                updateArray [i][3] = method;
                updateArray [i][4] = dateValue;
                updateArray [i][5] = timeValue;
                updateArray [i][6] = tailBase;
                updateArray [i][7] =  bandCode;
                updateArray [i][8] = shortBL;
                updateArray [i][9] = shortBN;
                updateArray [i][10] = bandEValue;
                updateArray [i][11] = beeEValue;
                updateArray [i][12] = rfidValue;
                updateArray [i][13] =  ageValue;
                updateArray [i][14] = sexValue;
                updateArray [i][15] = species;
                updateArray [i][16] = pox;

                ((MyApplication)getApplicationContext()).setArray(updateArray);
                int j = i + 1;
                ((MyApplication)getApplicationContext()).setCounter(j);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("keyArray", updateArray);
                saveValue.putExtras(mBundle);
                //saveValue.putExtra("fromMain", 0);
                startActivity(saveValue);
                finish();
            }
        });

        Button mainButton = (Button) findViewById(R.id.homeButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent saveValue = new Intent(tagFields.this, MainActivity.class);
                //startActivity(saveValue);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                EditText rfidText = (EditText) findViewById(R.id.rfidText);

                rfidText.setText(result);
            }
        }
    }//onActivityResult
}
