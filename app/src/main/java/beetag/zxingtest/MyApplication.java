package beetag.zxingtest;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class MyApplication extends Application {

    private Integer counter = 0;


    private int sendCounter = 0;

    private String[][] globalString = new String [50][20];

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public int getSendCounter() {
        return sendCounter;
    }

    public void setSendCounter(int sendCounter) {
        this.sendCounter = sendCounter;
    }

    public void addSendCounter() {
        sendCounter = sendCounter + 1;
    }


    public String[][] getArray(){
        return globalString;
    }

    public void setArray(String [][] globalString) {
        this.globalString = globalString;
    }

    public void resetArray(){
        globalString = new String [50][20];
    }

    public void deleteRow(int row) {
        ArrayList<String[]> temp = new ArrayList<>(Arrays.asList(globalString));
        temp.remove(row);
        globalString = temp.toArray(globalString);
        counter -= 1;
    }
}