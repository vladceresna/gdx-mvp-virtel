package com.vladceresna.virtel.modules.files;

import com.vladceresna.virtel.modules.system.SystemOperator;

public class FilesOperator {
    private static SystemOperator instance;

    public FilesOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
}
