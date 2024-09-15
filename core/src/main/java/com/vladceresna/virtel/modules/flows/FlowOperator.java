package com.vladceresna.virtel.modules.flows;

import com.vladceresna.virtel.modules.system.SystemOperator;

import java.util.List;

public class FlowOperator {
    private static SystemOperator instance;
    private List<Flow> flows;

    public FlowOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
}
