package com.vladceresna.virtel.modules.graphic;

import com.vladceresna.virtel.modules.system.SystemOperator;

public class GraphicOperator {
    private static SystemOperator instance;

    public GraphicOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
}
