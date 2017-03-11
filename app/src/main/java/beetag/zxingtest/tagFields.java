package beetag.zxingtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.logging.LoggingPermission;

/**
 * The tagFields class controls the aspects of the Tag Field screen
 */
public class tagFields extends AppCompatActivity {
    /**
     * Displays the formatted tag fields onto the current screen
     * @param savedInstanceState the data of the current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_fields);

    }
}
