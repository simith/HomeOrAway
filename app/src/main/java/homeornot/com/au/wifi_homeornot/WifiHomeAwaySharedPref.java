package homeornot.com.au.wifi_homeornot;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by simith on 18/04/17.
 */
public  class WifiHomeAwaySharedPref {


    static String homeWifiSSIDKey="HomeSSID";
    static String homeWifiBSSIDKey = "HomeBSSID";
    static String mode="currentMode"; //home or away

    // A -1 means we have taken off, pages 0,1,2,3 etc.

    public static SharedPreferences getSharedPref(Context pCtxt){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(pCtxt);
        return sp;
    }

    public static void saveHomeSSID(SharedPreferences pSp,String pSSID){

        SharedPreferences.Editor e = pSp.edit();
        e.putString(homeWifiSSIDKey,pSSID);
        e.apply();

    }

    public static String getHomeSSID(SharedPreferences pSp){

        return  pSp.getString(homeWifiSSIDKey,"");

    }

    public static void saveHomeBSSID(SharedPreferences pSp,String pBSSID){

        SharedPreferences.Editor e = pSp.edit();
        e.putString(homeWifiBSSIDKey,pBSSID);
        e.apply();

    }

    public static String getHomeBSSID(SharedPreferences pSp){

        return  pSp.getString(homeWifiBSSIDKey,"");

    }

    public static String getMode(SharedPreferences pSp){

        return  pSp.getString(mode,"");

    }

    public static void saveMode(SharedPreferences pSp,String pMode){

        SharedPreferences.Editor e = pSp.edit();
        e.putString(mode,pMode);
        e.apply();

    }



}