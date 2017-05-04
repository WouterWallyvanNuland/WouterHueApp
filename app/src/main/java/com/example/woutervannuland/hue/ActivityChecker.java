package com.example.woutervannuland.hue;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.util.List;

/**
 * @author Wouter.van.Nuland on 03/04/2017.
 */
public interface ActivityChecker {

    void ikHebAccessPointsGevonden(List<PHAccessPoint> dezeVondIk);
    void showHueOnConnectedBridge(PHBridge verbondenBridge);
}
