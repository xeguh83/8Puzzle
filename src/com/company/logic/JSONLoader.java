package com.company.logic;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by �������� ������ on 25.10.2015.
 */
public class JSONLoader {

    private final JSONObject jsonObject;

    public JSONLoader() throws IOException, ParseException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.json").getFile());

        //File file = new File("c:\\work\\config.json");

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        jsonObject = (JSONObject) obj;
    }

    public String getStrategy() {
        return (String) jsonObject.get("strategy");
    }


    public int[][] getStartData(){
        return getData("startdata");
    }

    public int[][] getEndData() {
        return getData("enddata");
    }

    private int[][] getData(String dataName) {
        int[][] data = new int[3][3];
        List list = (List) jsonObject.get(dataName);
        for (int j = 0; j < list.size(); j++) {
            List innerList = (List) list.get(j);
            for (int i = 0; i < innerList.size(); i++) {
                long dataLong = (Long)innerList.get(i);
                data[j][i] = (int) dataLong;
            }
        }
        return data;
    }




}
