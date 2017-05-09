package homeornot.com.au.wifi_homeornot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ScanResult> results;
    int size;
    WifiManager wifi;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifi = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button scanWifiButton = (Button) findViewById(R.id.startScanButton);
        scanWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent scanIntent;
                scanIntent = new Intent(MainActivity.this, StartWifiScanActivity.class);
                startActivity(scanIntent);

            }
        });



    }


    private void scanForWifiNetworks() {


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                results = wifi.getScanResults();
                size = results.size();
                Log.d("DEBUG", "Size of scan results is:" + size);

                for (ScanResult s : results) {

                    Log.d("DEBUG", "AP name:" + s.SSID + ", BSSID:" + s.BSSID);
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifi.startScan();

    }

    private void listenForWifiNetworks() {

        IntentFilter i = new IntentFilter();
        //i.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        i.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent i) {
                // TODO Auto-generated method stub

                Log.d("DEBUG", "Entering onReceive");
                WifiManager w = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                /*List<ScanResult> l = w.getScanResults();

                for (ScanResult r : l) {
                    //use r.SSID or r.BSSID to identify your home network and take action
                    Log.d("DEBUG",r.SSID + "" + r.level + "\r\n");
                }*/


                SupplicantState state = (SupplicantState) i.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
                switch (state) {
                    case COMPLETED: {
                        Log.d("DEBUG", "COMPLETED state");
                    }
                    break;
                    case DISCONNECTED: {
                        Log.d("DEBUG", "DISCONNECTED state");
                    }
                    break;
                    case INTERFACE_DISABLED: {
                        Log.d("DEBUG", "INTERFACE_DISABLED state");
                    }
                    break;
                    case AUTHENTICATING: {
                        Log.d("DEBUG", "AUTHENTICATING state");
                    }
                    break;
                    case ASSOCIATED: {
                        Log.d("DEBUG", "ASSOCIATED state");
                    }
                    break;
                }
                if (w.isWifiEnabled()) {
                    Log.d("DEBUG", "WiFi is enabled");
                    WifiInfo info = w.getConnectionInfo();

                    Log.d("DEBUG", "SSID: " + info.getSSID() + "BSSID: " + info.getBSSID());
                }

            }
        };
        // registerReceiver(receiver, i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
