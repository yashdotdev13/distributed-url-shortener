package com.yashdotdev.url_service.generator;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class AtomicIdGenerator implements IdGenerator {

    private final AtomicLong sequence = new AtomicLong(0);


    @Override
    public long nextId() {
        return sequence.getAndIncrement();
    }
}
