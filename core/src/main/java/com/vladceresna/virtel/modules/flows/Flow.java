package com.vladceresna.virtel.modules.flows;

import com.vladceresna.virtel.modules.apps.App;
import com.vladceresna.virtel.modules.apps.AppsOperator;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

import lombok.Data;

@Data
public class Flow {
    private final UUID flowId;
    private ActionsWrapper actionsWrapper;

    private String appId;
    public Flow(String appId) {
        this.appId = appId;
        this.flowId = UUID.randomUUID();
        this.actionsWrapper = new ActionsWrapper();
    }

    public UUID getFlowId() {
        return flowId;
    }

    @SuppressWarnings("NewApi")
    public void run(String fileName){
        try {
            AppsOperator appsOperator = AppsOperator.getInstance();
            App appById = appsOperator.getAppById(appId);
            File file = appById.getBinWithInAppPath(fileName);

            String code = Files.readString(file.toPath());
            System.out.println(code);//TODO:edit

            int lineNumber = 0;
            String lastString = "";
            Step step = new Step();
            byte word = 0;
            boolean inValue = false;
            boolean displayer = false;
            //lexing
            for (int i = 0; i < code.length(); i++) {
                char c = code.charAt(i);
                switch (c) {
                    case ' ':
                        if (inValue) {
                            lastString += ' ';
                        } else {
                            switch (word) {
                                case 0:
                                    step.mod = lastString.trim();
                                    break;
                                case 1:
                                    step.cmd = lastString;
                                    break;
                                default:
                                    step.args.add(lastString);
                                    break;
                            }
                            lastString = "";
                            word++;
                        }
                        if (displayer) displayer = false;
                        break;
                    case '\\':
                        displayer = !displayer;
                        lastString += '\\';
                        break;
                    case '"':
                        if (displayer) {
                            displayer = false;
                        } else {
                            inValue = !inValue;
                            lastString += '"';
                        }
                        break;
                    case ';':
                        step.args.add(lastString);
                        try {
                            exec(step);
                        } catch (Exception e) {
                            System.out.println("Error on line " + lineNumber + ": " + e.getMessage());
                            e.printStackTrace();
                            break;
                        }
                        lineNumber++;
                        lastString = "";
                        step = new Step();
                        word = 0;
                        inValue = false;
                        if (displayer) displayer = false;
                        break;
                    default:
                        lastString += c;
                        if (displayer) displayer = false;
                        break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exec(Step step) throws Exception {
        //parsing
        switch (step.mod) {
            case "sys":
                switch (step.cmd) {
                    case "out":
                        actionsWrapper.sysOut(step.args);
                        break;
                    case "in":
                        actionsWrapper.sysIn(step.args);
                        break;
                    default:
                        throw new Exception("Undefined command: "+step.cmd+" in module: "+step.mod);
                }
                break;
            case "var":
                switch (step.cmd){
                    case "set":
                        actionsWrapper.varSet(step.args);
                        break;
                    case "del":
                        actionsWrapper.varDel(step.args);
                        break;
                    default:
                        throw new Exception("Undefined command: "+step.cmd+" in module: "+step.mod);
                }
                break;
            default:
                throw new Exception("Undefined module: "+step.mod);
        }
    }

}
