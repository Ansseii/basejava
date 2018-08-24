package com.mysite.basejava;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainStream {


    public static void main(String[] args) {

        int[] array = new int[]{1, 2, 3, 3, 2, 3};
        System.out.println(minValue(array));

    }

    private static int minValue(final int[] values) {

        return Integer.parseInt(Arrays.stream(values)
                .distinct()
                .sorted()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining()));
    }
}
