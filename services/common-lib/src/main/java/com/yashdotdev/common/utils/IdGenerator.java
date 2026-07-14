package com.yashdotdev.common.utils;

import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {

    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
