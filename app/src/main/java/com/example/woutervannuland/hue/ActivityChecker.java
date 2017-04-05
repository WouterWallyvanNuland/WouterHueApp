package com.example.woutervannuland.hue;

import com.philips.lighting.model.PHBridge;

/**
 * Created by Wouter.van.Nuland on 03/04/2017.
 */

public interface ActivityChecker {

    public void showHueOnConnectedBridge(PHBridge verbondenBridge);
    public void changingLightColors(PHBridge receivedBridge);

}
