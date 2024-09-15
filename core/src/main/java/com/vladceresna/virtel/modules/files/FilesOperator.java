package com.vladceresna.virtel.modules.files;

import com.vladceresna.virtel.modules.system.SystemOperator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesOperator {
    private static FilesOperator instance;

    public FilesOperator(){

    }
    public static FilesOperator getInstance() {
        if (instance == null) {
            instance = new FilesOperator();
        }
        return instance;
    }

    public List<File> scanDirectory(File directory) {
        File[] files = directory.listFiles();
        List<File> res = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    res.addAll(scanDirectory(file));
                } else {
                    res.add(file);
                }
            }
        }
        return res;
    }
    public String getVirtelPath(){
        SystemOperator systemOperator = SystemOperator.getInstance();
        return systemOperator.getVirtelPath();
    }
    public String getAppPath(String appId){
        return getVirtelPath()+"apps/"+appId;
    }
    public String getAppBinPath(String appId){
        return getAppPath(appId)+"/bin/";
    }
    public String getInAppPathOfFile(File file, String appId){
        String res = "";
        boolean appIdIs = false;
        String[] split = file.getPath().split("[\\\\/]");
        for (String sector: split){
            if (sector.equals(appId)){
                appIdIs = true;
                continue;
            }
            if(!sector.equals("bin")) {
                if (appIdIs && sector.equals(file.getName())) {
                    res += sector;
                } else if (appIdIs) {
                    res += sector + "/";
                }
            }
        }
        return res;
    }

}
