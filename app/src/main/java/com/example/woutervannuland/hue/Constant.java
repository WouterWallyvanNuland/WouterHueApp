package com.example.woutervannuland.hue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wouter.van.Nuland on 07/04/2017.
 */

public final class Constant {

    public static final String EIGEN_LAMPEN_IP = "192.168.2.20";
    public static final String RICK_LAMPEN_IP = "192.168.2.3";
    public static final String FOURTRESS_LAMPEN_IP1 = "172.16.10.76";
    public static final String FOURTRESS_LAMPEN_IP2 = "172.16.10.92";

    public static String GEKOZEN_IP;
 //   public static final String GEKOZEN_IP = FOURTRESS_LAMPEN_IP2;
//    private String GEKOZEN_IP = RICK_LAMPEN_IP;
//    private String GEKOZEN_IP = EIGEN_LAMPEN_IP;


    public static String[] getHistoryIp() {
        return HISTORY_IP;
    }

    public List ips = new ArrayList<String>();




    public static final String[] HISTORY_IP = {"192.168.2.20", "192.168.2.3", "172.16.10.92", "172.16.10.76"};



    }






