package com.goldze.mvvmhabit.utils;

public class Constants {
    private static Constants INSTANCE;

    private Constants() {

    }

    public static Constants getInstance() {
        if (INSTANCE == null) INSTANCE = new Constants();
        return INSTANCE;
    }

    public static final String[] DEVICE_ID_LIST = {"51ac8c03", "356BCHTSQ4TD", "FUQKHY5TQGLNC6DM"};
}
