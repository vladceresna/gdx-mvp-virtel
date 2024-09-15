package com.vladceresna.virtel.modules.data;

import com.vladceresna.virtel.modules.system.SystemOperator;

public class DataOperator {
    private static SystemOperator instance;

    public DataOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
}
