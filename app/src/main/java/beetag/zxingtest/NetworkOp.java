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
        String sendDate = arrayReceived[0];
        String sendTime = arrayReceived[1];
        String sendBand = arrayReceived[2];
        String sendBee = arrayReceived[3];
        String sendRFID = arrayReceived[4];
        String sendBandCode = arrayReceived[5];
        String sendShortLetter = arrayReceived[6];
        String sendShortBand = arrayReceived[7];
        String sendSpecies = arrayReceived[8];
        String sendPox = arrayReceived[9];
        String sendTailColor = arrayReceived[10];
        String sendAge = arrayReceived[11];
        String sendSex = arrayReceived[12];
        String sendCapture = arrayReceived[13];
        String sendLifeStatus = arrayReceived[14];

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
                    .appendQueryParameter("Date", sendDate)
                    .appendQueryParameter("Time", sendTime)
                    .appendQueryParameter("BandNumber", sendBand)
                    .appendQueryParameter("BEETagNumber", sendBee)
                    .appendQueryParameter("RFIDNumber", sendRFID)
                    .appendQueryParameter("BandCode", sendBandCode)
                    .appendQueryParameter("ShortBandLetter", sendShortLetter)
                    .appendQueryParameter("ShortBandNumber", sendShortBand)
                    .appendQueryParameter("SpeciesID", sendSpecies)
                    .appendQueryParameter("PoxLesions2015", sendPox)
                    .appendQueryParameter("TailBaseColor", sendTailColor)
                    .appendQueryParameter("AgeCode", sendAge)
                    .appendQueryParameter("SexCode", sendSex)
                    .appendQueryParameter("CaptureMethod", sendCapture)
                    .appendQueryParameter("LifeStatus", sendLifeStatus);
            String query = builder.build().getEncodedQuery();

            // Log.d("OUTPUT QUERY", query);
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
