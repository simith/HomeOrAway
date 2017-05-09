package homeornot.com.au.wifi_homeornot.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import homeornot.com.au.wifi_homeornot.WifiHomeAwaySharedPref;

/**
 * Created by simith on 20/04/17.
 */
public class ModesManager {

    public static String HOME="home";
    public static String AWAY="away";

    public boolean isWifiConnected(Context pCtxt){

        boolean isWifiConnected = false;
        ConnectivityManager connManager = (ConnectivityManager) pCtxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            isWifiConnected = true;
        }
        return isWifiConnected;
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
        return null;
    }

    public String getWifiBSSID(Context pCtxt)
    {
        WifiManager mainWifi = (WifiManager) pCtxt.getSystemService(pCtxt.WIFI_SERVICE);
        WifiInfo currentWifi = mainWifi.getConnectionInfo();
        if(currentWifi != null)
        {
            if(currentWifi.getBSSID() != null)
            {
                return currentWifi.getBSSID();
            }
        }
        return null;
    }

    //check whether we are connected to Home AP, and set mode accordingly
    public void updateMode(Context pCtxt){

        if(isWifiConnected(pCtxt)){

            String userPreferenceHomeSSID = WifiHomeAwaySharedPref.getHomeSSID(WifiHomeAwaySharedPref.getSharedPref(pCtxt));
            String userPreferenceHomeBSSID = WifiHomeAwaySharedPref.getHomeBSSID(WifiHomeAwaySharedPref.getSharedPref(pCtxt));

            String connectedSSID = this.getWifiSSID(pCtxt);
            String connectedBSSID = this.getWifiBSSID(pCtxt);

            //connectedSSID has an extra start and end quote
            if (connectedSSID.startsWith("\"") && connectedSSID.endsWith("\"")){
                connectedSSID = connectedSSID.substring(1, connectedSSID.length()-1);
            }

            if(connectedSSID.equals(userPreferenceHomeSSID) &&
                    connectedBSSID.equals(userPreferenceHomeBSSID)){
                WifiHomeAwaySharedPref.saveMode(WifiHomeAwaySharedPref.getSharedPref(pCtxt),HOME);
            }
            else
            {
                WifiHomeAwaySharedPref.saveMode(WifiHomeAwaySharedPref.getSharedPref(pCtxt),AWAY);
            }

        }
        else
        {
            WifiHomeAwaySharedPref.saveMode(WifiHomeAwaySharedPref.getSharedPref(pCtxt),AWAY);
        }

    }


}
