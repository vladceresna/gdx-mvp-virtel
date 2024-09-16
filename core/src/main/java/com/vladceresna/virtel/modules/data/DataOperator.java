package com.vladceresna.virtel.modules.data;

import java.util.HashMap;

public class DataOperator {
    private static DataOperator instance;
    private HashMap<DataType, HashMap<String, Object>> database = new HashMap<>();
    public DataOperator(){

    }
    public static DataOperator getInstance() {
        if (instance == null) {
            instance = new DataOperator();
        }
        return instance;
    }
    public void saveData(DataType dataType, String name, Object value){
        HashMap<String, Object> dataset = getDataset(dataType);
        dataset.put(name, value);
        database.put(dataType, dataset);
    }
    public Object findData(DataType dataType, String name){
        HashMap<String, Object> dataset = getDataset(dataType);
        return dataset.get(name);//TODO:need exception
    }
    public Object delData(DataType dataType, String name){
        HashMap<String, Object> dataset = getDataset(dataType);
        return dataset.remove(name);//TODO:need exception
    }
    public HashMap<String, Object> getDataset(DataType dataType){
        if (database.containsKey(dataType)){
            return database.get(dataType);
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();
            database.put(dataType,hashMap);
            return hashMap;
        }
    }
}
