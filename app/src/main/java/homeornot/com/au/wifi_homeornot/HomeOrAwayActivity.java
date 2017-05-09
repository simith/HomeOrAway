package homeornot.com.au.wifi_homeornot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import homeornot.com.au.wifi_homeornot.data.BroadcastMessage;
import homeornot.com.au.wifi_homeornot.utils.ModesManager;

public class HomeOrAwayActivity extends AppCompatActivity {

    private  BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_or_away);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateMode();
        registerHomeOrAwayListener();
    }

    private void updateMode() {

        String lMode = WifiHomeAwaySharedPref.getMode(WifiHomeAwaySharedPref.getSharedPref(this));

        if(lMode !=null && lMode.equals(ModesManager.HOME)) {
            setHomeMode();
        }
        else if(lMode !=null && lMode.equals(ModesManager.AWAY)){
            setAwayMode();
        }


    }

    public void onPause(){

        super.onPause();
        unRegisterHomeOrAwayListener();
    }


    public void onResume(){

        super.onResume();
        registerHomeOrAwayListener();
        updateMode();

    }

    private void setHomeMode(){
        ImageView imgView = (ImageView) findViewById(R.id.homeoraway);
        TextView  txtView = (TextView) findViewById(R.id.homeOrAwayTxt);

        imgView.setImageResource(R.drawable.homeicon);
        txtView.setText("Home");


    }

    private void setAwayMode(){
        ImageView imgView = (ImageView) findViewById(R.id.homeoraway);
        TextView  txtView = (TextView) findViewById(R.id.homeOrAwayTxt);

        imgView.setImageResource(R.drawable.redaway);
        txtView.setText("Away");
    }




    private void registerHomeOrAwayListener() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastMessage.BROADCAST_HOME_WIFI_DISCONNECTED);
        intentFilter.addAction(BroadcastMessage.BROADCAST_HOME_WIFI_CONNECTED);

        this.mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(BroadcastMessage.BROADCAST_HOME_WIFI_DISCONNECTED) ) {

                    setAwayMode();
                }
                else
                {
                    setHomeMode();
                }

            }
        };

        registerReceiver(this.mBroadcastReceiver, intentFilter);


    }

    private void unRegisterHomeOrAwayListener() {

        if(this.mBroadcastReceiver != null) {
            unregisterReceiver(this.mBroadcastReceiver);
            this.mBroadcastReceiver = null;
        }
    }


}
