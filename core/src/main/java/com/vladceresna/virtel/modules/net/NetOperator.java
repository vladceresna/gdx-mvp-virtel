package com.vladceresna.virtel.modules.net;

import com.vladceresna.virtel.modules.system.SystemOperator;

public class NetOperator {
    private static SystemOperator instance;

    public SystemOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
}
