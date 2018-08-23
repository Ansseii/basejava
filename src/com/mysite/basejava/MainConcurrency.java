package com.mysite.basejava;

public class MainConcurrency {

    public static void main(String[] args) {
        int a = 0;
        int b = 1;

        deadlock(a, b);
        deadlock(b, a);
    }

    private static void deadlock(final Object firstLocker, final Object secondLocker) {

        new Thread(() -> {
            synchronized (firstLocker) {
                System.out.println(firstLocker);
                synchronized (secondLocker) {
                    System.out.println("Wait another locker");
                    System.out.println(secondLocker);
                }
            }
        }).start();
    }
}
