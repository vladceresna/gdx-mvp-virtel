package com.vladceresna.virtel.modules.flows;

import com.badlogic.gdx.utils.Logger;
import com.vladceresna.virtel.modules.data.DataOperator;
import com.vladceresna.virtel.modules.data.DataType;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ActionsWrapper {
    private DataOperator dataOperator = DataOperator.getInstance();
    private Scanner scanner;
    private FileReader reader;
    private FileWriter writer;
    private Logger logger = new Logger("VRTL");

    /**sys out args**/
    public void sysOut(List<String> args) throws Exception {
        for (String arg:args) System.out.print(unwrap(arg, ArgType.CLEAR_VALUE));
    }
    /**sys in var**/
    public void sysIn(List<String> args) throws Exception {
        scanner = new Scanner(System.in);
        scanner.close();
    }
    /**var set value var**/
    public void varSet(List<String> args) throws Exception {
        dataOperator.saveData(
            DataType.VAR,
            unwrap(args.get(1),ArgType.VAR_NAME),
            unwrap(args.get(0),ArgType.CLEAR_VALUE)
        );
    }
    /**var del var**/
    public String varDel(List<String> args) throws Exception {
        return (String) dataOperator.delData(
            DataType.VAR,
            unwrap(args.get(0),ArgType.VAR_NAME)
        );
    }


    /**Special for getting in expressions**/
    public String varGet(List<String> args) throws Exception {
        return (String) dataOperator.findData(DataType.VAR,args.get(0));
    }
    public String unwrap(String arg, ArgType type) throws Exception {
        String trimmed = arg.trim();
        if (type.equals(ArgType.CLEAR_VALUE)) {
            if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
                return trimmed.replaceAll("\"", "");
            } else {
                return varGet(Collections.singletonList(trimmed));
            }
        } else if(type.equals(ArgType.VAR_NAME)){
            if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
                throw new Exception("Unexpected token: \" in arg: "+trimmed+": there must be var name");
            } else {
                return trimmed;
            }
        }
        return null;//TODO:need exception;
    }

}
