package homeornot.com.au.wifi_homeornot.data;

/**
 * Created by simith on 16/04/17.
 */
public class WiFiAp {

    String SSID;
    String BSSID;

    public String getSSID() {
        return SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}
