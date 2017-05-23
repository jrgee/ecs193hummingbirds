package beetag.zxingtest;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class NetworkOp extends AsyncTask<String, String, String[]> {
    //int row = ((MyApplication)getApplicationContext()).getCounter();
    private Exception exception;

    protected String[] doInBackground(String[] arrayReceived) {

        return arrayReceived;
    }

    protected void onPostExecute(String[] arrayReceived) {

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

        String[] recorderSplit = sendRecorder.split(" ");
        String[] speciesSplit = sendSpecies.split(" ");
        String[] locationSplit = sendLocation.split(" ");
        String[] ageSplit = sendAge.split(" ");
        String[] sexSplit = sendSex.split(" ");
        String[] tailSplit = sendTailColor.split(" ");


        URL url = null;
        try {
            url = new URL("http://data.hummingbirdhealth.org/transfer.aspx");

            HttpURLConnection conn = null;
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
                    .appendQueryParameter("BandCode", sendBandCode)
                    .appendQueryParameter("ShortBandLetter", sendShortLetter)
                    .appendQueryParameter("ShortBandNumber", sendShortBand)
                    .appendQueryParameter("BandNumber", sendBand)
                    .appendQueryParameter("BEETagNumber", sendBee)
                    .appendQueryParameter("RFIDNumber", sendRFID)
                    .appendQueryParameter("AgeCode", ageSplit[0])
                    .appendQueryParameter("SexCode", sexSplit[0])
                    .appendQueryParameter("SpeciesID", speciesSplit[0])
                    .appendQueryParameter("PoxLesions2015", sendPox);

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

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
