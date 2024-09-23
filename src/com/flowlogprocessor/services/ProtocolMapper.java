package com.flowlogprocessor.services;

import java.util.HashMap;
import java.util.Map;

public class ProtocolMapper {
    private static final Map<Integer, String> PROTOCOL_MAP = new HashMap<>();

    static {
        PROTOCOL_MAP.put(1, "ICMP");
        PROTOCOL_MAP.put(6, "TCP");
        PROTOCOL_MAP.put(17, "UDP");
        PROTOCOL_MAP.put(41, "IPv6");
        // We can add more as needed
    }

    public static String getProtocolKeyword(int protocolNumber) {
        return PROTOCOL_MAP.get(protocolNumber);
    }
}
