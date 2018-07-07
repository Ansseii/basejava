package com.mysite;

import com.mysite.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {

    public static void main(String[] args) throws   IllegalAccessException,
                                                    NoSuchMethodException,
                                                    InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        //HW
        System.out.println(r.getClass().getMethod("toString").invoke(r));
    }

}
