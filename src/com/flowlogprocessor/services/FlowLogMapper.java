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

/**
 * Processes flow logs and computes the counts for tags and port/protocol combinations
 */
public class FlowLogMapper {
    private static final String UNTAGGED_KEY = "Untagged";

    /**
     * Maps flow logs based on the provided lookup table and writes the results to an output file.
     * @param flowRecords a list of FlowLogRecord objects
     * @param lookupTable a map containing LookupKeyObjects as keys and their corresponding tags as values
     * @param outputFile the path of the file where the output will be written
     * @throws FlowLogException if there is an error while writing to the output file
     */
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
            } else {
                currentTag = UNTAGGED_KEY;
            }
            portProtocolCombinationCounts.put(currentLookupKeyObject, portProtocolCombinationCounts.getOrDefault(currentLookupKeyObject, 0) + 1);
            tagMatchCounts.put(currentTag, tagMatchCounts.getOrDefault(currentTag, 0) + 1);
        }
        writeToOutputFile(tagMatchCounts, portProtocolCombinationCounts, outputFile);
    }

    /**
     * Writes the tag match counts and port/protocol combination counts to the specified output file.
     * @param tagMatchCounts a map of tags and their corresponding count of records matched
     * @param portProtocolCombinationCounts a map of port/protocol combinations and their corresponding counts
     * @param outputFile the path of the file where the output will be written
     * @throws FlowLogException if there is an error while writing to the output file
     */
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
