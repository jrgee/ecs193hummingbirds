package beetag.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import 	android.graphics.drawable.Drawable;

import com.google.zxing.common.BitMatrix;

public class drawActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        context = getApplicationContext();
        final Drawable white = ContextCompat.getDrawable(context,R.drawable.white);
        final Drawable black = ContextCompat.getDrawable(context,R.drawable.black);
        final BitMatrix bits = new BitMatrix(5,5);
        bits.setRegion(0,0,5,5);



        Button drawButton = (Button) findViewById(R.id.scan_button);
        drawButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                BitMatrix reader = bits;
                int dec = -1;
                for (int i = 0; i < 4; i++) {
                    dec = decode(reader);
                    if (dec != -1) {
                        //Pass BeeTag info to next screen
                        //debugInfo.setText("");
                        Intent saveIntent= new Intent(drawActivity.this, cameraActivity.class);
                        saveIntent.putExtra("decimal", Integer.toString(dec));
                        startActivity(saveIntent);
                    } else {
                        reader = rotate(reader, 5);
                        //Log.d("bits", bits.toString());
                    }
                }
                if (dec == -1) {
                    Intent saveIntent= new Intent(drawActivity.this, MainActivity.class);
                    saveIntent.putExtra("debugInfo", "BEEtag invalid");
                    startActivity(saveIntent);
                }
            }
        });



        ToggleButton toggle0 = (ToggleButton) findViewById(R.id.button0);
        toggle0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(0,0);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(0,0);
                    // The toggle is disabled
                }
            }
        });


        ToggleButton toggle1 = (ToggleButton) findViewById(R.id.button1);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(1,0);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(1,0);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle2 = (ToggleButton) findViewById(R.id.button2);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(2,0);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(2,0);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle3 = (ToggleButton) findViewById(R.id.button3);
        toggle3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(3,0);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(3,0);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle4 = (ToggleButton) findViewById(R.id.button4);
        toggle4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(4,0);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(4,0);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle5 = (ToggleButton) findViewById(R.id.button5);
        toggle5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(0,1);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(0,1);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle6 = (ToggleButton) findViewById(R.id.button6);
        toggle6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(1,1);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(1,1);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle7 = (ToggleButton) findViewById(R.id.button7);
        toggle7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(2,1);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(2,1);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle8 = (ToggleButton) findViewById(R.id.button8);
        toggle8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(3,1);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(3,1);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle9 = (ToggleButton) findViewById(R.id.button9);
        toggle9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(4,1);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(4,1);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle10 = (ToggleButton) findViewById(R.id.button10);
        toggle10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(0,2);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(0,2);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle11 = (ToggleButton) findViewById(R.id.button11);
        toggle11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(1,2);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(1,2);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle12 = (ToggleButton) findViewById(R.id.button12);
        toggle12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(2,2);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(2,2);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle13 = (ToggleButton) findViewById(R.id.button13);
        toggle13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(3,2);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(3,2);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle14 = (ToggleButton) findViewById(R.id.button14);
        toggle14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(4,2);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(4,2);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle15 = (ToggleButton) findViewById(R.id.button15);
        toggle15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(0,3);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(0,3);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle16 = (ToggleButton) findViewById(R.id.button16);
        toggle16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(1,3);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(1,3);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle17 = (ToggleButton) findViewById(R.id.button17);
        toggle17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(2,3);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(2,3);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle18 = (ToggleButton) findViewById(R.id.button18);
        toggle18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(3,3);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(3,3);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle19 = (ToggleButton) findViewById(R.id.button19);
        toggle19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(4,3);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(4,3);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle20 = (ToggleButton) findViewById(R.id.button20);
        toggle20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(0,4);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(0,4);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle21 = (ToggleButton) findViewById(R.id.button21);
        toggle21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(1,4);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(1,4);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle22 = (ToggleButton) findViewById(R.id.button22);
        toggle22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(2,4);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(2,4);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle23 = (ToggleButton) findViewById(R.id.button23);
        toggle23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(3,4);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(3,4);
                    // The toggle is disabled
                }
            }
        });

        ToggleButton toggle24 = (ToggleButton) findViewById(R.id.button24);
        toggle24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(4,4);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(4,4);
                    // The toggle is disabled
                }
            }
        });


    }

    BitMatrix rotate(BitMatrix bits, int size){
        BitMatrix x = new BitMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(bits.get(size - j - 1, i))
                    x.set(i, j);
            }
        }
        return x;
    }



    int decode(BitMatrix bits){
        int dec = 0; //stores decimal number of tag
        int[] par = new int[5]; //stores expected parity results

        //convert BitMatrix into decimal representation and set column parity bits
        for(int i=0; i<3; i++){ //for first 3 columns
            par[i] = 0; //initialize to even (0)

            for(int j=0; j<5; j++){ //for each row
                if(!bits.get(i, j)) { //if white square (representing 1)
                    dec += Math.pow(2, 14 - 5 * i - j);
                    par[i] = 1 - par[i]; //flip parity for each white
                }
            }
        }

        //set last two parity bits
        par[3] = 0; //initialize to even (0)
        for(int j=0; j<3; j++){ //for first 3 rows
            for(int i=0; i<3; i++){ //for first 3 columns
                if(!bits.get(i, j)) { //if white square (representing 1)
                    par[3] = 1 - par[3]; //flip parity for each white
                }
            }
        }

        par[4] = 0; //initialize to even (0)
        for(int j=3; j<5; j++){ //for last 2 rows
            for(int i=0; i<3; i++){ //for first 3 columns
                if(!bits.get(i, j)) { //if white square (representing 1)
                    par[4] = 1 - par[4]; //flip parity for each white
                }
            }
        }

        //check parity for 4th column
        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(3, j) && par[j] == 0) || (bits.get(3, j) && par[j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 1 failed.");
                return -1;
            }
        }

        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(4, j) && par[4-j] == 0) || (bits.get(4, j) && par[4-j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 2 failed.");
                return -1;
            }
        }

        return dec;
    }

}



