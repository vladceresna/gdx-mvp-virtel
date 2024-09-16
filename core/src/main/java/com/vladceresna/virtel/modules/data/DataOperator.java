package com.vladceresna.virtel.modules.data;

import com.vladceresna.virtel.modules.system.SystemOperator;

import java.util.HashMap;
import java.util.List;

public class DataOperator {
    private static SystemOperator instance;
    private HashMap<DataType, HashMap<String, Object>> database;
    public DataOperator(){

    }
    public static SystemOperator getInstance() {
        if (instance == null) {
            instance = new SystemOperator();
        }
        return instance;
    }
    public void saveData(DataType dataType, String name, Object value){
        HashMap<String, Object> dataset = database.get(dataType);
        dataset.put(name, value);
        database.put(dataType, dataset);
    }
    public Object findData(DataType dataType, String name){
        HashMap<String, Object> dataset = database.get(dataType);
        return dataset.get(name);//TODO:need exception
    }
}
