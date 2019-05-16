package groep10.chef.database;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by thoma on 13/04/2018.
 */

public class HTTPDataHandler {
    static String stream = null;

    public HTTPDataHandler(){
    }

    public String getHTTPData(String urlstring){
        try{
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line=r.readLine()) !=null){
                sb.append(line);
            }
            stream = sb.toString();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
