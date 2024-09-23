package com.flowlogprocessor.exceptions;

/**
 * The FlowLogException class is a custom exception that is thrown when there are issues while processing flow logs
 * This extends the Exception class to provide specific error handling for flow log operations.
 */
public class FlowLogException extends Exception{
    public FlowLogException(String message, Throwable cause) {
        super(message, cause);
    }
}
