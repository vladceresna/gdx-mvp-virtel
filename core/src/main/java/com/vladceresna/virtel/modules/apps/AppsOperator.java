package com.vladceresna.virtel.modules.apps;

import com.vladceresna.virtel.modules.files.FilesOperator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppsOperator {
    private static AppsOperator instance;
    private List<App> apps = new ArrayList<>();

    public static AppsOperator getInstance() {
        if (instance == null) {
            instance = new AppsOperator();
        }
        return instance;
    }
    public App getAppById(String appId){
        for (App app:apps) if (app.getAppId().equals(appId)) return app;
        return null;//TODO:need exception
    }
    public void runApp(String appId){
        App app = new App(appId);
        apps.add(app);
        FilesOperator filesOperator = FilesOperator.getInstance();
        app.setBins(filesOperator.scanDirectory(new File(filesOperator.getAppBinPath(appId))));
        app.start();
    }
}
