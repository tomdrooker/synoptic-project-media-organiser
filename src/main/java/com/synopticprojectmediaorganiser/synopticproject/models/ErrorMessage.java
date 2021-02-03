package com.synopticprojectmediaorganiser.synopticproject.models;

public class ErrorMessage {
    private String errorHeading;
    private String errorMessage;

    public ErrorMessage(String errorHeading, String errorMessage) {
        this.errorHeading = errorHeading;
        this.errorMessage = errorMessage;
    }

    public String getErrorHeading() {
        return errorHeading;
    }

    public void setErrorHeading(String errorHeading) {
        this.errorHeading = errorHeading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

