package com.hr.recruitmenthandler.util;


public class JobApplicationError {

    private String errorMessage;

    public JobApplicationError(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
