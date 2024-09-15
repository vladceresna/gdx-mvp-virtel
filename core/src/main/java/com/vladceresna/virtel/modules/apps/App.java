package com.vladceresna.virtel.modules.apps;

import com.vladceresna.virtel.modules.files.FilesOperator;
import com.vladceresna.virtel.modules.flows.Flow;
import com.vladceresna.virtel.modules.flows.FlowOperator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {
    private String appId;
    private String path;
    private List<File> bins = new ArrayList<>();
    private File cache;
    private List<Flow> flows = new ArrayList<>();
    public App(String appId){
        this.appId = appId;
    }
    public void start(){
        FlowOperator flowOperator = FlowOperator.getInstance();
        flowOperator.newFlow(appId,"start.steps");
    }
    public File getStartBin(){
        return getBinWithInAppPath("start.steps");
    }
    public File getBinWithInAppPath(String inAppPath){
        FilesOperator filesOperator = FilesOperator.getInstance();
        for (File bin:bins) if(filesOperator.getInAppPathOfFile(bin, appId).equals(inAppPath))
            return bin;
        return null;//TODO:need exception
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addFlow(Flow flow){
        flows.add(flow);
    }

    public List<File> getBins() {
        return bins;
    }

    public void setBins(List<File> bins) {
        this.bins = bins;
    }
}
