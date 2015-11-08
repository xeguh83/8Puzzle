package com.company.logic;

import java.io.*;
import java.util.Iterator;

/**
 * @author aturkin
 */
public class FileUploader {
    public void writeSolutionToFile(Iterator<State> solutionIterator) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        //File file = new File(classLoader.getResource("output.txt").getFile());
        File file = new File("c:\\work\\output.txt");

        PrintWriter printWriter = new PrintWriter(file);
        while (solutionIterator.hasNext()) {
            printWriter.println(solutionIterator.next().toString());
        }
        printWriter.close();
    }
}
