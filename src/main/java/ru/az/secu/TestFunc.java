package ru.az.equips;

import java.util.UUID;

public class TestFunc {

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.nameUUIDFromBytes("user".getBytes()).toString());
    }

}
