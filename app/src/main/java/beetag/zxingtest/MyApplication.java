package beetag.zxingtest;

import android.app.Application;


public class MyApplication extends Application {

    private Integer counter = 0;
    private String[][] globalString = new String [30][20];

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