package com.vladceresna.virtel.modules.flows;

import com.vladceresna.virtel.modules.apps.App;
import com.vladceresna.virtel.modules.apps.AppsOperator;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.UUID;

import lombok.Data;

@Data
public class Flow {
    private final UUID flowId;

    private String appId;
    public Flow(String appId) {
        this.appId = appId;
        this.flowId = UUID.randomUUID();
    }

    public UUID getFlowId() {
        return flowId;
    }

    public void run(String fileName){
        try {
            AppsOperator appsOperator = AppsOperator.getInstance();
            App appById = appsOperator.getAppById(appId);
            File file = appById.getBinWithInAppPath(fileName);
            FileReader reader = new FileReader(file);
            Scanner sc = new Scanner(reader);
            int lineNumber = 0;
            while(sc.hasNextLine()) {
                try {
                    exec(sc.nextLine());
                } catch (Exception e){
                    System.out.println("Error on line "+lineNumber+": "+e.getMessage());
                    break;
                }
                lineNumber++;
            }
            sc.close();
            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exec(String line) throws Exception{
        String lastString = "";
        Step step = new Step();
        byte word = 0;
        boolean inValue = false;
        boolean displayer = false;
        //lexing
        for (int i = 0; i<line.length(); i++){
            char c = line.charAt(i);
            switch (c){
                case ' ':
                    if(inValue){
                        lastString += ' ';
                    }else{
                        switch (word){
                            case 0:
                                step.mod = lastString;
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
                    break;
                case '\\':
                    displayer = !displayer;
                    break;
                case '"':
                    if(displayer){
                        displayer = false;
                    } else {
                        inValue = !inValue;
                        lastString += '"';
                    }
                    break;
                default:
                    lastString += c;
                    break;
            }
        }
        step.args.add(lastString);

        //parsing
        switch (step.mod) {
            case "sys":
                switch (step.cmd){
                    case "out":
                        for (String arg:step.args){
                            System.out.print(arg);
                        }
                        break;
                }
        }
    }

}
