package com.example.woutervannuland.hue.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHBridgeConfiguration;

/**
 * @author Rick Slinkman
 */

public class BridgeStorage
{
    private final String STORAGE_IP = "bridge_storage.ip";
    private final String STORAGE_MAC = "bridge_storage.mac";
    private final String STORAGE_USERNAME = "bridge_storage.user";
    private final String STORAGE_USAGE = "bridge_storage.usage";
    private final SharedPreferences mStorage;

    public BridgeStorage(Context context) {
        mStorage = context.getSharedPreferences("bridge_storage", Context.MODE_PRIVATE);
    }

    public void storeBridge(PHBridgeConfiguration bridge) {
        SharedPreferences.Editor editor = mStorage.edit();

        editor.putString(STORAGE_IP, bridge.getIpAddress());
        editor.putString(STORAGE_MAC, bridge.getMacAddress());
        editor.putString(STORAGE_USERNAME, bridge.getUsername());
        editor.putBoolean(STORAGE_USAGE, true);

        editor.apply();
    }

    public PHAccessPoint loadStoredBridge() {
        PHAccessPoint ap = null;

        boolean storageUsed = mStorage.getBoolean(STORAGE_USAGE, false);
        if(storageUsed) {
            String ip = mStorage.getString(STORAGE_IP, "");
            String mac = mStorage.getString(STORAGE_MAC, "");
            String username = mStorage.getString(STORAGE_USERNAME, "");
            // Create from stored values
            ap = new PHAccessPoint(ip, username, mac);
        }
        return ap;
    }

    public void forgetStoredBridge() {
        mStorage.edit().clear().apply();
    }
}
