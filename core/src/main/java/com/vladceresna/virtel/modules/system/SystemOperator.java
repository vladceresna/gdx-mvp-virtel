package com.vladceresna.virtel.modules.system;


public class SystemOperator {
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