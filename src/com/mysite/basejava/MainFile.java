package com.mysite.basejava;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {

        recursivePrint("../basejava/src/com/mysite/basejava");

    }

    public static void recursivePrint(String pathname) {
        File dir = new File(pathname);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    System.out.println(file.getName());
                } else {
                    recursivePrint(file.getAbsolutePath());
                }
            }
        }
    }
}
