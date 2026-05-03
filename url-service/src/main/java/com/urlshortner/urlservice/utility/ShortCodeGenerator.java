package com.urlshortner.urlservice.utility;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final SecureRandom random = new SecureRandom();

    public String generateCode(int length){
        StringBuilder code = new StringBuilder();

        for(int i=0;i<length;i++){
            int index = random.nextInt(BASE62.length());
            code.append(BASE62.charAt(index));
        }

        return code.toString();
    }

}
