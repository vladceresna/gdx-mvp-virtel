package com.vladceresna.virtel.modules.flows;

import java.util.List;

import lombok.Data;

@Data
public class Step {
    private String mod;
    private String cmd;
    private List<String> arguments;
    public String getArgument(int index){
        return arguments.get(index);
    }
}
