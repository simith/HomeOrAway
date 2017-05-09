package homeornot.com.au.wifi_homeornot;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import homeornot.com.au.wifi_homeornot.data.WiFiAp;
import homeornot.com.au.wifi_homeornot.data.WifiListAdapter;
import homeornot.com.au.wifi_homeornot.utils.ModesManager;

public class StartWifiScanActivity extends AppCompatActivity {




    int size;
    WifiManager wifi;
    WifiListAdapter mListAdapter;
    ListView mListView;
    private BroadcastReceiver mBroadcastReceiver;
    private ModesManager mModesMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_wifi_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mListView = (ListView) findViewById(R.id.listView);
        this.mListAdapter = new WifiListAdapter(this,0,new LinkedList<WiFiAp>());
        this.mListView.setAdapter(this.mListAdapter);
        setSupportActionBar(toolbar);
        this.mModesMgr = new ModesManager();

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String selected = ((TextView) view.findViewById(R.id.wifiApName)).getText().toString();
                WiFiAp lAp = (WiFiAp) mListView.getAdapter().getItem(position);
                SharedPreferences lSp = WifiHomeAwaySharedPref.getSharedPref(getApplicationContext());
                WifiHomeAwaySharedPref.saveHomeSSID(lSp, lAp.getSSID());
                WifiHomeAwaySharedPref.saveHomeBSSID(lSp, lAp.getBSSID());

                mModesMgr.updateMode(StartWifiScanActivity.this);

                //start the final activity
                Intent lIntent = new Intent();

                lIntent = new Intent(StartWifiScanActivity.this, HomeOrAwayActivity.class);
                startActivity(lIntent);


                //   Toast toast = Toast.makeText(getApplicationContext(), lAp.getBSSID(), Toast.LENGTH_SHORT);
                //   toast.show();
            }
        });


        wifi = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        getWifi();

    }




    public String getWifiSSID(Context pCtxt)
    {
        WifiManager mainWifi = (WifiManager) pCtxt.getSystemService(pCtxt.WIFI_SERVICE);
        WifiInfo currentWifi = mainWifi.getConnectionInfo();
        if(currentWifi != null)
        {
            if(currentWifi.getSSID() != null)
            {
                return currentWifi.getSSID();
            }
        }
        return "NOT_SURE";
    }





    private void scanForWifiNetworks() {

    final WifiListAdapter lAdapter = this.mListAdapter;

        this.mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                List<ScanResult> results;
                List<WiFiAp> list = new LinkedList<WiFiAp>();
                results = wifi.getScanResults();
                size = results.size();


                Log.d("DEBUG", "Size of scan results is:" + size);



                for (ScanResult s : results) {
                    WiFiAp lAp = new WiFiAp();
                    Log.d("DEBUG", "AP name:" + s.SSID + ", BSSID:" + s.BSSID);
                    lAp.setSSID(s.SSID);
                    lAp.setBSSID(s.BSSID);
                    list.add(lAp);


                }
                Log.d("Debug", "Calling notifyDataSet changed");
                lAdapter.clear();
                lAdapter.addAll(list);
                lAdapter.notifyDataSetChanged();

                /*StartWifiScanActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        //do your modifications here
                        lAdapter.clear();
                        lAdapter.addAll(list);
                        lAdapter.notifyDataSetChanged();
                    }
                });*/


            }
        };

        registerScanResultBroadcastReceiver();

        wifi.startScan();

    }

    private void registerScanResultBroadcastReceiver() {

        registerReceiver(this.mBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void unRegisterScanResultsBroadcastReceiver(){

        unregisterReceiver(this.mBroadcastReceiver);
    }

    // call this method only if you are on 6.0 and up, otherwise call doGetWifi()
    @TargetApi(Build.VERSION_CODES.M)
    private void getWifi() {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);
        } else {
            scanForWifiNetworks(); // the actual wifi scanning
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            scanForWifiNetworks();
        }
    }

    public void onPause(){

        super.onPause();
        unRegisterScanResultsBroadcastReceiver();

    }

    public void onDestroy(){

        super.onDestroy();
        unRegisterScanResultsBroadcastReceiver();
    }

    public void onResume(){

        super.onResume();
        registerScanResultBroadcastReceiver();
    }

}
