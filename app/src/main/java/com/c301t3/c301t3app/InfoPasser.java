package com.c301t3.c301t3app;

import android.os.Bundle;

/**
 * Created by Henry on 2018-03-17.
 */
/**
 * This class represents a singleton for passing data between
 * activities via a bundle.
 *
 * HOW TO USE:
 * For loading in info to send to another Activity
 *  final InfoPasser infoInstance = InfoPasser.getInstance();
 *  Bundle bundleName = new Bundle();
 *  bundleName.putData(key, data); <- putData(...) doesn't exist, only for demo
 *  bundleName.setInfo(bundleName);
 * For retrieving info from another Activity
 *  final InfoPasser infoInstance = InfoPasser.getInstance();
 *  Bundle bundleName = infoInstance.getInfo();
 *  Data data =  bundleName.getData(key);
 *
 * @author Henry
 * @version 1.0
 */
class InfoPasser {
    private static final InfoPasser ourInstance = new InfoPasser();
    private Bundle info = new Bundle();

    static InfoPasser getInstance() {
        return ourInstance;
    }

    private InfoPasser() {}

    public void setInfo(Bundle bundle) {
        this.info = bundle;
    }

    public Bundle getInfo() {
        return this.info;
    }

}
