package com.mercury.exception;

import java.time.LocalDateTime;

public class ErrorResponse<E> {

    private LocalDateTime timestamp;
    private Integer status;
    private E errorDetails;
    private String path;
    private String error;

    public ErrorResponse() {}

    public ErrorResponse(LocalDateTime timestamp, Integer status, E errorDetails, String path, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorDetails = errorDetails;
        this.path = path;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public E getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(E errorDetails) {
        this.errorDetails = errorDetails;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
