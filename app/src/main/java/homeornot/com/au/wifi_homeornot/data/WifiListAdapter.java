package homeornot.com.au.wifi_homeornot.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import homeornot.com.au.wifi_homeornot.R;

public class WifiListAdapter extends ArrayAdapter<WiFiAp>{


    public WifiListAdapter(Context context, int resource, List<WiFiAp> pWiFiApList) {
        super(context, 0,pWiFiApList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public WiFiAp getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(WiFiAp item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WiFiAp lWifiAp = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
        }
        // Lookup view for data population
        TextView wifiApName = (TextView) convertView.findViewById(R.id.wifiApName);
        wifiApName.setText(lWifiAp.getSSID());
        // Return the completed view to render on screen
        return convertView;

    }
}