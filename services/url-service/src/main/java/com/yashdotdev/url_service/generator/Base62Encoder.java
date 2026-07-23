package com.yashdotdev.url_service.generator;


import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final char[] CHARACTERS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                    .toCharArray();

    private static final int BASE = CHARACTERS.length;

    public String encode(long value) {

        if (value == 0) {
            return "0";
        }

        StringBuilder builder = new StringBuilder();

        while (value > 0) {

            int remainder = (int) (value % BASE);

            builder.append(CHARACTERS[remainder]);

            value /= BASE;
        }

        return builder.reverse().toString();
    }
}
