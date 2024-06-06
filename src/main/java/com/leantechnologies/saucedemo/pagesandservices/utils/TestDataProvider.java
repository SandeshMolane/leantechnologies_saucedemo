package com.leantechnologies.saucedemo.pagesandservices.utils;

import org.testng.annotations.DataProvider;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestDataProvider {

    private final static String runFlag = "isRun";

    @DataProvider(name = "csvDataProvider", parallel = true)
    public Iterator<Object[]> csvDataProvider(Method method){
        List<Object[]> l1 = new ArrayList<>();
        if(null != method.getAnnotation(TestDataFile.class)){
            String filePath = method.getAnnotation(TestDataFile.class).path();
            filePath = "testdata/" + filePath;
            readFile(filePath)
                    .stream()
                    .filter(this::evaluateIsRun)
                    .forEach(m -> {
                        l1.add(m.values().toArray());
                    });
            return l1.iterator();
        }
        readFile(method)
                .stream()
                .filter(this::evaluateIsRun)
                .forEach(m -> {
                    l1.add(m.values().toArray());
                });

        return l1.iterator();
    }

    private LinkedList<LinkedHashMap<String, String>> readFile( Method method) {
        Class<?> cls = method.getDeclaringClass();
        String className = cls.getSimpleName();

        String fileName = className + "." + method.getName() + ".csv";
        String subPackage = "";
        String testCasePackage = cls.getPackage().toString();
        if(testCasePackage.indexOf("com.scripts.") != -1) {
            String[] subPackageArr = testCasePackage.split("com.scripts");
            subPackage = subPackageArr[1].replaceAll("\\.", "/");
            subPackage = subPackage + "/";
        }
        String filePath = "";
        String filePathDefault = "";

        filePath = "testdata/" + subPackage + fileName;
        filePathDefault = "testdata/" + subPackage + fileName;

        return readFile(filePathDefault);
    }

    private LinkedList<LinkedHashMap<String, String>> readFile(String filePath){
        ClassLoader cl = getClass().getClassLoader();
        URL resource = cl.getResource(filePath);
        String[] line;

        boolean isFirstRow = true;
        try(CSVReader br = new CSVReader(new FileReader(resource.getFile()))){
            String[] columnNames = new String[0];
            LinkedList<LinkedHashMap<String, String>> l = new LinkedList<>();
            int rowNum = 0;
            while((line = br.readNext()) != null) {
                ArrayList<String> cells = new ArrayList<>();
                LinkedHashMap<String, String> data = new LinkedHashMap<>();
                for(String cell : line) {
                    cells.add(cell);
                }
                if(isFirstRow) {
                    columnNames = cells.toArray(new String[cells.size()]);
                    cells.clear();
                    isFirstRow = false;
                    continue;
                }

                if(columnNames.length != cells.size()) {
                    continue;
                }

                for(int index = 0; index < cells.size(); index++) {
                    data.put(columnNames[index], cells.get(index));
                }
                l.add(data);
            }
            return l;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean evaluateIsRun(Map<String, String> map) {
        if (map.containsKey(runFlag) || map.containsKey(runFlag.toLowerCase()) || map.containsKey(runFlag.toUpperCase())) {
            AtomicBoolean isRun = new AtomicBoolean(false);
            map.forEach((k, v) -> {
                if(k.equalsIgnoreCase(runFlag) && v.equalsIgnoreCase("Y")) {
                    isRun.set(true);
                }
            });
            return isRun.get();
        } else
            return true;
    }


}
