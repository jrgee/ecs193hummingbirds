package beetag.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import 	android.graphics.drawable.Drawable;

import com.google.zxing.common.BitMatrix;

public class drawActivity extends AppCompatActivity {

    Context context;
    Drawable white;
    Drawable black;
    BitMatrix bits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = getApplicationContext();
        white = ContextCompat.getDrawable(context,R.drawable.white);
        black = ContextCompat.getDrawable(context,R.drawable.black);
        bits = new BitMatrix(5,5);
        bits.setRegion(0,0,5,5);

        Button drawButton = (Button) findViewById(R.id.scan_button);
        drawButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                BitMatrix reader = bits;
                int dec = -1;
                for (int i = 0; i < 4; i++) {
                    dec = MainActivity.decode(reader);
                    if (dec != -1) {
                        //Pass BeeTag info to next screen
                        Intent saveIntent= new Intent(drawActivity.this, cameraActivity.class);
                        saveIntent.putExtra("binary", MainActivity.matToBinString(reader));
                        saveIntent.putExtra("decimal", Integer.toString(dec));
                        startActivity(saveIntent);
                        finish();
                        break;
                    } else {
                        reader = MainActivity.rotate(reader, 5);
                    }
                }
                if (dec == -1) {
                    Toast toast = Toast.makeText(context, "Invalid BEEtag", Toast.LENGTH_SHORT);
                    toast.show();
                    /*
                    Intent saveIntent= new Intent(drawActivity.this, MainActivity.class);
                    saveIntent.putExtra("debugInfo", "BEEtag invalid");
                    startActivity(saveIntent);
                    finish();
                    */
                }
            }
        });

        ToggleButton toggle[] = new ToggleButton[25];
        final int[] ids = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14,
                R.id.button15, R.id.button16, R.id.button17, R.id.button18, R.id.button19,
                R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24
        };

        for(int i=0; i<25; i++){
            toggle[i] = (ToggleButton)findViewById((ids[i]));
            toggle[i].setOnCheckedChangeListener(SetOnChecked(i));
        }

    }

    private CompoundButton.OnCheckedChangeListener SetOnChecked(final int i){
        final int row = i % 5;
        final int col = i / 5;

        CompoundButton.OnCheckedChangeListener listen = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, white, null, null);
                    bits.unset(row, col);
                    // The toggle is enabled
                } else {
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, black, null, null);
                    bits.set(row, col);
                    // The toggle is disabled
                }
            }
        };

        return listen;
    }
}



