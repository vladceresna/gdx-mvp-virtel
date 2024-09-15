package com.vladceresna.virtel.modules.flows;

import com.vladceresna.virtel.modules.apps.App;
import com.vladceresna.virtel.modules.apps.AppsOperator;
import com.vladceresna.virtel.modules.system.SystemOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlowOperator {
    private static FlowOperator instance;
    private List<Flow> flows = new ArrayList<>();

    public FlowOperator(){

    }
    public static FlowOperator getInstance() {
        if (instance == null) {
            instance = new FlowOperator();
        }
        return instance;
    }
    public void newFlow(String appId, String fileName){
        App app = AppsOperator.getInstance().getAppById(appId);
        Flow flow = new Flow(appId);
        Thread thread = new Thread(() -> flow.run(fileName));
        flows.add(flow);
        app.addFlow(flow);
        thread.start();
    }

    public Flow getFlowById(UUID id){
        for (Flow flow:flows) if (flow.getFlowId().equals(id)) return flow;
        return null;//TODO:need exception
    }
}
