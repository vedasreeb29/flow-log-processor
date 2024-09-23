package com.flowlogprocessor.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps a Protocol in decimal to the protocol's string keyword for processing
 */
public class ProtocolMapper {
    private static final Map<Integer, String> PROTOCOL_MAP = new HashMap<>();

    static {
        PROTOCOL_MAP.put(1, "ICMP");
        PROTOCOL_MAP.put(6, "TCP");
        PROTOCOL_MAP.put(17, "UDP");
        PROTOCOL_MAP.put(41, "IPv6");
        // Additional protocols can be added as required
    }

    /**
     * Retrieves the protocol keyword associated with the specified protocol number.
     * @param protocolNumber the decimal identifier of the protocol
     * @return the corresponding protocol keyword, or null if the protocol number is not found in the map
     */
    public static String getProtocolKeyword(int protocolNumber) {
        return PROTOCOL_MAP.get(protocolNumber);
    }
}
