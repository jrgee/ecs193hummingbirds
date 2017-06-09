package beetag.zxingtest;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkOp extends AsyncTask<String, String, String[]> {
    //int row = ((MyApplication)getApplicationContext()).getCounter();
    private Exception exception;
    private Context context;
    private TableLayout stk;

    public NetworkOp(Context context, TableLayout stk) {
        this.context = context;
        this.stk = stk;
    }


    protected String[] doInBackground(String[] arrayReceived) {

        return arrayReceived;
    }



    protected void onPostExecute(String[] arrayReceived) {
        Toast toast = Toast.makeText(context, "Sending line " + arrayReceived[19], Toast.LENGTH_LONG);
        int success = 0;
        String sendRecorder = arrayReceived[0];
        String sendLocation = arrayReceived[1];
        String sendLifeStatus = arrayReceived[2];
        String sendCapture = arrayReceived[3];
        String sendDate = arrayReceived[4];
        String sendTime = arrayReceived[5];
        String sendTailColor = arrayReceived[6];
        String sendBandCode = arrayReceived[7];
        String sendShortLetter = arrayReceived[8];
        String sendShortBand = arrayReceived[9];
        String sendBand = arrayReceived[10];
        String sendBee = arrayReceived[11];
        String sendRFID = arrayReceived[12];
        String sendAge = arrayReceived[13];
        String sendSex = arrayReceived[14];
        String sendSpecies = arrayReceived[15];
        String sendPox = arrayReceived[16];
        String sendColor = arrayReceived[17];
        String sendNote = arrayReceived[18];
        String sendRFIDHex = arrayReceived[19];

        String[] recorderSplit = sendRecorder.split(" ");
        String[] speciesSplit = sendSpecies.split(" ");
        String[] locationSplit = sendLocation.split(" ");
        String[] ageSplit = sendAge.split(" ");
        String[] sexSplit = sendSex.split(" ");
        String[] tailSplit = sendTailColor.split(" ");
        String[] colorSplit = sendColor.split(" ");
        String[] bandCodeSplit = sendBandCode.split(" ");



        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL("http://data.hummingbirdhealth.org/transfer.aspx/");

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("Recorder", recorderSplit[0])
                    .appendQueryParameter("Location", locationSplit[0])
                    .appendQueryParameter("LifeStatus", sendLifeStatus)
                    .appendQueryParameter("CaptureMethod", sendCapture)
                    .appendQueryParameter("Date", sendDate)
                    .appendQueryParameter("Time", sendTime)
                    .appendQueryParameter("TailBaseColor", tailSplit[0])
                    .appendQueryParameter("BandCode", bandCodeSplit[0])
                    .appendQueryParameter("ShortBandLetter", sendShortLetter)
                    .appendQueryParameter("ShortBandNumber", sendShortBand)
                    //.appendQueryParameter("BandNumber", sendBand)
                    .appendQueryParameter("BEETagNumber", sendBee)
                    .appendQueryParameter("RFIDNumber", sendRFID)
                    .appendQueryParameter("RFIDNumberHex", sendRFIDHex)
                    .appendQueryParameter("BEETagColor", colorSplit[0])

                    .appendQueryParameter("AgeCode", ageSplit[0])
                    .appendQueryParameter("SexCode", sexSplit[0])
                    .appendQueryParameter("SpeciesID", speciesSplit[0])
                    .appendQueryParameter("PoxLesions2015", sendPox)
                    .appendQueryParameter("Notes", sendNote);

            String query = builder.build().getEncodedQuery();

            Log.d("OUTPUT QUERY", query);
            OutputStream os = null;
            os = conn.getOutputStream();
            BufferedWriter writer = null;
            writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            success = 1;

        } catch (Exception e){
            //e.printStackTrace();
            //Log.d("Close connection", "fail");
            toast.cancel();
            toast = Toast.makeText(context , "Connection failed", Toast.LENGTH_SHORT);
            toast.show();
        }finally {
            conn.disconnect();
            //Log.d("Close connection", "success");
        }

        if(success == 1){
            toast.cancel();
            toast = Toast.makeText(context , "Line " + arrayReceived[19] + " sent", Toast.LENGTH_SHORT);
            toast.show();
            int row = Integer.parseInt(arrayReceived[19]);
            ((MyApplication)context).deleteRow(row-1); //remove from global array
            stk.removeViewAt(row); //remove from table

            //Log.d("full rows", ((MyApplication)context).getCounter().toString());
            if(Integer.parseInt(arrayReceived[19]) == 1){ //if top row sent
                toast.cancel();
                toast = Toast.makeText(context , "All data sent to server", Toast.LENGTH_SHORT);
                toast.show();
                ((MyApplication)context).setCounter(0);
                ((MyApplication)context).setSendCounter(0);
                //((MyApplication)context).resetArray();
            }

        }
    }

}
