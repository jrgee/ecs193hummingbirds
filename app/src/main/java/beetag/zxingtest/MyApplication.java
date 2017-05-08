package beetag.zxingtest;

import android.app.Application;

/**
 * Created by giheok on 5/7/17.
 */

public class MyApplication extends Application {

    private Integer counter = 0;
    private String[][] globalString = new String [2][5];

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String[][] getArray(){
        return globalString;
    }

    public void setArray(String [][] globalString) {
        this.globalString = globalString;
    }
}