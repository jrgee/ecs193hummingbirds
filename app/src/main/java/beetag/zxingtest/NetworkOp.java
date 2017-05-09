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

class NetworkOp extends AsyncTask<String[], String, String[][]> {
    //int row = ((MyApplication)getApplicationContext()).getCounter();
    private Exception exception;

    protected String[][] doInBackground(String[][] arrayReceived) {

        return arrayReceived;
    }

    protected void onPostExecute(String[][] arrayReceived) {
        String sendDate = arrayReceived[0][0];
        String sendTime = arrayReceived[0][1];
        String sendBand = arrayReceived[0][2];
        String sendBee = arrayReceived[0][3];
        String sendRFID = arrayReceived[0][4];

        URL url = null;
        try {
            url = new URL("http://data.hummingbirdhealth.org");

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
                    .appendQueryParameter("Band", sendBand)
                    .appendQueryParameter("Bee", sendBee)
                    .appendQueryParameter("RFID", sendRFID);
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
