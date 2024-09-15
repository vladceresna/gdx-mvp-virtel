package com.vladceresna.virtel.modules.flows;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Flow {
    public Flow(){

    }
    public void run(String fileName){
        try {
            File file = new File(fileName);
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
    private String lastString;
    public void exec(String line) throws Exception{
        lastString = "";
        for (int i = 0; i<line.length(); i++){
            switch (line.charAt(i)){
                case ' ':

                case '/':

                case '"':

                default:
                    
            }
        }
    }
}
