package homeornot.com.au.wifi_homeornot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import homeornot.com.au.wifi_homeornot.data.BroadcastMessage;
import homeornot.com.au.wifi_homeornot.utils.ModesManager;

/**
 * Created by simith on 11/04/17.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI && activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {

            Intent i = new Intent(BroadcastMessage.BROADCAST_HOME_WIFI_CONNECTED);
            context.sendBroadcast(i);
            SharedPreferences lSp = WifiHomeAwaySharedPref.getSharedPref(context);
            WifiHomeAwaySharedPref.saveMode(lSp, ModesManager.HOME);
            Toast.makeText(context, "Wifi Connected! network" + getWifiSSID(context), Toast.LENGTH_SHORT).show();


        } else if(activeNetInfo != null &&  activeNetInfo.getType() != ConnectivityManager.TYPE_WIFI
                && activeNetInfo.getState() == NetworkInfo.State.CONNECTED)
        {
             //we are connected to something which is not WIFI
            Intent i = new Intent(BroadcastMessage.BROADCAST_HOME_WIFI_DISCONNECTED);
            context.sendBroadcast(i);
            SharedPreferences lSp = WifiHomeAwaySharedPref.getSharedPref(context);
            WifiHomeAwaySharedPref.saveMode(lSp, ModesManager.AWAY);
            Toast.makeText(context, "Connected to Mobile", Toast.LENGTH_SHORT).show();
        }


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
}