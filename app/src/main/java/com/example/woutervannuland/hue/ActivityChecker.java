package com.example.woutervannuland.hue;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHBridge;

import java.util.List;

/**
 * Created by Wouter.van.Nuland on 03/04/2017.
 */

public interface ActivityChecker {

    void onAccessPointsFound(List<PHAccessPoint> list);

    void onAuthenticationRequired(PHAccessPoint phAccessPoint);

    public void showHueOnConnectedBridge(PHBridge verbondenBridge);
    public void changingLightColors(PHBridge receivedBridge);

}
