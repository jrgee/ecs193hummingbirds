package beetag.zxingtest;

import android.app.Application;


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
}