package com.yashdotdev.url_service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortCodeGenerator {

    private final IdGenerator idGenerator;

    private final Base62Encoder base62Encoder;

    public String generate() {

        long id = idGenerator.nextId();
        return base62Encoder.encode(id);
    }

}