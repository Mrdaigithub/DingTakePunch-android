package com.goldze.mvvmhabit.utils;

public class Constants {
    private static Constants INSTANCE;

    private Constants() {

    }

    public static Constants getInstance() {
        if (INSTANCE == null) INSTANCE = new Constants();
        return INSTANCE;
    }

    public static final String[] DEVICE_ID_LIST = {"111", "222", "333", "444"};
}
