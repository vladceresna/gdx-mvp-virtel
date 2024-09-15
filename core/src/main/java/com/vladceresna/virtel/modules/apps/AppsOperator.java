package com.vladceresna.virtel.modules.apps;

import com.vladceresna.virtel.modules.system.SystemOperator;

public class AppsOperator {
    private static SystemOperator instance;

    public AppsOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
}
