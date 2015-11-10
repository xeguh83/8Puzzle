package com.company.logic;

import java.io.*;
import java.util.Iterator;

/**
 * Класс выгружающий список пройденных состояний в файл
 * @author aturkin
 */
public class FileUploader {
    /**
     * Запись содержимого итератора в текстовый файл output.txt находящийся либо в файлах ресурсов (для Linux),
     * либо по пути c:\work\output.txt (для Windows)
     * @param solutionIterator итератор содержащий последовательность строковых представлений состояний
     *                         для выгрузки в файл
     * @throws IOException в случае проблем доступа к файлу
     */
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
