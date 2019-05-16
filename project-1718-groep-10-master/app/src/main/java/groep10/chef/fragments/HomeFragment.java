package groep10.chef.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import groep10.chef.Main;
import groep10.chef.R;
import groep10.chef.database.Common;
import groep10.chef.database.HTTPDataHandler;

/**
 * Created by ruben on 10-Apr-18.
 */

public class HomeFragment extends Fragment {
    private TaskCallbacks mCallbacks;
    private Main main;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        main = (Main) getActivity();
        setRetainInstance(true);

        SharedPreferences preferences = main.getSharedPreferences("shared",main.MODE_PRIVATE);

        List<String> localFiles = Arrays.asList(main.fileList());
        if(( ! localFiles.contains(main.RECIPE_FILE) ) // Er is nog geen bestand voor de recepten op te slaan gemaakt
                || ( ! localFiles.contains(main.INGREDIENT_FILE) // Er is nog geen bestand voor de ingredienten op te slaan gemaakt
                || preferences.getString(main.LAST_DOWNLOAD, null).compareTo(getLastSunday()) < 0 // Er is sinds de laatste download van de database een zondag gepasseerd (data-update)
        )){
            if(! isNetworkAvailable()){
                checkConnectionAlert();
            } else {
                downloadData();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentVG,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_screen, parentVG, false);
        return v;
    }

    // interface die Main moet implementeren om zijn UI aan te passen aan Task updates
    public interface TaskCallbacks{
        void onPreExecute1();
        void onPostExecute1(String s);

        void onPreExecute2();
        void onPostExecute2(String s);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    public class GetData1 extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            if(mCallbacks != null) {
                mCallbacks.onPreExecute1();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream=null;
            String urlString = strings[0];

            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(mCallbacks != null) {
                mCallbacks.onPostExecute1(s);
            }
        }
    }
    class GetData2 extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            if(mCallbacks != null) {
                mCallbacks.onPreExecute2();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream=null;
            String urlString = strings[0];

            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(mCallbacks != null) {
                mCallbacks.onPostExecute2(s);
            }
        }
    }

    // Verbindt met de databank, downloadt de data en scrhijft deze naar 2 files
    private void downloadData(){
        // Hiermee wordt de data van de database opgehaald en naar 2 files geschreven
        new GetData1().execute(Common.getAddressAPI());
        new GetData2().execute(Common.getAddressAPI2());

        // De huidige datum wordt in de SharedPreferences gestoken als laatste download van de databank
        SharedPreferences preferences = main.getSharedPreferences("shared",main.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Date currentDate = new Date();
        edit.putString(main.LAST_DOWNLOAD, formatter.format(currentDate));
        edit.apply();
    }

    // Deze functie creeert een dialog om te vragen een internet connectie te maken indien er geen is
    private void checkConnectionAlert() {
        final AlertDialog.Builder connectionAlert = new AlertDialog.Builder(main);
        connectionAlert.setTitle("No internet connection");
        connectionAlert.setMessage("Please check your internet connection");
        connectionAlert.setCancelable(false);
        connectionAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!isNetworkAvailable()) {
                    dialogInterface.dismiss();
                    checkConnectionAlert();
                } else {
                    dialogInterface.dismiss();
                    downloadData();
                }
            }
        });
        AlertDialog alert = connectionAlert.create();
        alert.show();
    }

    // functie om na te kijken of het apparaat een internconnectie heeft
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) main.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // geeft een string terug die de laatste zondag voorstelt, in het formaat yyyyMMdd
    private String getLastSunday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        int days = cal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        date.setTime(date.getTime() - (long) (days*1000*60*60*24));
        return dateFormat.format(date);
    }
}
