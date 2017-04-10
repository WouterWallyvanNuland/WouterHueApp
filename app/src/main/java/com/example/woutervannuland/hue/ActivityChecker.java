package com.example.woutervannuland.hue;

import com.philips.lighting.model.PHBridge;

/**
 * @author Wouter.van.Nuland on 03/04/2017.
 */
public interface ActivityChecker {
    void showHueOnConnectedBridge(PHBridge verbondenBridge);
}
