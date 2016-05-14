package com.github.yaroslavguschak.onlinelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yars on 30.04.2016.
 */
public class FileOperator {

    public static byte[] readBytesFromFileAndDel(File inputFile) throws IOException {
//        File inputFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] fileBytes = new byte[(int) inputFile.length()];
        inputStream.read(fileBytes);
        inputStream.close();
        inputFile.delete();
        return fileBytes;
    }

    public static byte[] readBytesFromFile(File inputFile) throws IOException {
//        File inputFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] fileBytes = new byte[(int) inputFile.length()];
        inputStream.read(fileBytes);
        inputStream.close();
        return fileBytes;
    }

    public static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(fileBytes);
        outputStream.close();
    }

    public static void saveBytesToFile(File file, byte[] fileBytes) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(fileBytes);
        outputStream.close();
    }

}
