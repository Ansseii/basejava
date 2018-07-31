package com.mysite.basejava;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {

        recursivePrint("../basejava/src", "");

    }

    public static void recursivePrint(final String pathname, final String interval) {
        File dir = new File(pathname);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    System.out.println(interval + file.getName());
                } else {
                    System.out.println(interval + file.getName());
                    recursivePrint(file.getAbsolutePath(), interval + " ");
                }
            }
        }
    }
}
