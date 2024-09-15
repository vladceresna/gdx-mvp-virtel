package com.vladceresna.virtel.modules.system;


import com.badlogic.gdx.utils.Logger;
import com.vladceresna.virtel.modules.apps.AppsOperator;

public class SystemOperator {
    private static SystemOperator instance;
    private String virtelPath;

    public SystemOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
    public void start(String virtelPath){
        this.virtelPath = virtelPath;
        try{
            AppsOperator appsOperator = AppsOperator.getInstance();
            appsOperator.runApp("vladceresna.virtel.launcher");
        }catch (Exception e){
            new Logger("ERRR").debug("Thats wrong: "+e.getMessage());
            start(virtelPath);
        }
    }
    public String getVirtelPath(){
        return virtelPath;
    }
}
