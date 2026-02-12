package com.mropdkt.relaycore.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private  static String toJson(Object obl){
        try {
            return mapper.writeValueAsString(obl);
        } catch (Exception e) {
            throw new RuntimeException("Json conversion failed.");
        }
    }
}
