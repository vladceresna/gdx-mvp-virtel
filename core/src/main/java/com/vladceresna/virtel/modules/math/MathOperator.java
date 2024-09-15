package com.vladceresna.virtel.modules.math;

import com.vladceresna.virtel.modules.system.SystemOperator;

public class MathOperator {
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
