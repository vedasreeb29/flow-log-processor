package com.flowlogprocessor.services;

import com.flowlogprocessor.exceptions.FlowLogException;
import com.flowlogprocessor.models.LookupKeyObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads the Lookup table from file
 */
public class LookupTableReader {
    private final String lookupTableFile;

    /**
     * Constructs a LookupTableReader with the specified lookup table file path.
     * @param lookupTableFile the path to the lookup table file
     */
    public LookupTableReader(String lookupTableFile) {
        this.lookupTableFile = lookupTableFile;
    }

    /**
     * Generates a lookup table by reading from the specified lookup table file.
     * Each entry in the table maps a LookupKeyObject (consisting of destination port and protocol) to its corresponding tag.
     * @return a map of LookupKeyObjects and their corresponding tags
     * @throws FlowLogException if there is an error while reading the lookup file or if the file is not found
     */
    public Map<LookupKeyObject, String> generateLookupTable() throws FlowLogException{
        try(BufferedReader reader = new BufferedReader(new FileReader(this.lookupTableFile))) {
            Map<LookupKeyObject, String> lookupTable = new HashMap<>();

            // Skip the header line
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null) {
                String[] lookupFields = line.split(",");
                int destinationPort = Integer.parseInt(lookupFields[0]);
                String protocol = lookupFields[1];
                String tag = lookupFields[2];

                LookupKeyObject lookupKey = new LookupKeyObject(destinationPort, protocol);
                lookupTable.put(lookupKey, tag);
            }

            return lookupTable;
        } catch(FileNotFoundException e) {
            throw new FlowLogException("Lookup file not found, please provide correct full path for the lookup file.", e);
        } catch (IOException e) {
            throw new FlowLogException("Error while reading the lookup file.", e);
        }
    }
}
