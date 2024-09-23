package com.flowlogprocessor.services;

import com.flowlogprocessor.exceptions.FlowLogException;
import com.flowlogprocessor.models.FlowLogRecord;
import com.flowlogprocessor.models.LookupKeyObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowLogMapper {
    private static final String UNTAGGED_KEY = "Untagged";

    public void mapFlowLogs(List<FlowLogRecord> flowRecords, Map<LookupKeyObject, String> lookupTable, String outputFile) throws FlowLogException {
        Map<String, Integer> tagMatchCounts = new HashMap<>();
        Map<LookupKeyObject, Integer> portProtocolCombinationCounts = new HashMap<>();

        for(FlowLogRecord flowLogRecord: flowRecords) {
            int destinationPort = flowLogRecord.getDestinationPort();
            int protocolDecimal = flowLogRecord.getProtocol();
            String protocolKeyword = ProtocolMapper.getProtocolKeyword(protocolDecimal).toLowerCase();

            LookupKeyObject currentLookupKeyObject = new LookupKeyObject(destinationPort, protocolKeyword);
            String currentTag;
            if(lookupTable.containsKey(currentLookupKeyObject)) {
                currentTag = lookupTable.get(currentLookupKeyObject);
                portProtocolCombinationCounts.put(currentLookupKeyObject, portProtocolCombinationCounts.getOrDefault(currentLookupKeyObject, 0) + 1);
            } else {
                currentTag = UNTAGGED_KEY;
            }
            tagMatchCounts.put(currentTag, tagMatchCounts.getOrDefault(currentTag, 0) + 1);
        }
        writeToOutputFile(tagMatchCounts, portProtocolCombinationCounts, outputFile);
    }

    private void writeToOutputFile(Map<String, Integer> tagMatchCounts,
                                   Map<LookupKeyObject, Integer> portProtocolCombinationCounts,
                                   String outputFile) throws FlowLogException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write Count of matches for each tag
            writer.write("Tag Counts:\n");
            writer.write("Tag,Count\n");
            for(Map.Entry<String, Integer> entry:tagMatchCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }

            // Write Count of matches for each port/protocol combination
            writer.write("Port/Protocol Combination Counts:\n");
            writer.write("Port,Protocol,Count\n");
            for(Map.Entry<LookupKeyObject, Integer> entry:portProtocolCombinationCounts.entrySet()) {
                writer.write(entry.getKey().getDestinationPort() + "," + entry.getKey().getProtocol() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            throw new FlowLogException("Error while writing the output to a file.", e);
        }
    }
}
