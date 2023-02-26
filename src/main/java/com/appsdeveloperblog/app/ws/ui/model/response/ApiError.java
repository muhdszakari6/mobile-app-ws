package com.appsdeveloperblog.app.ws.ui.model.response;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiError {
    private HttpStatus status;
    private String message;
    private Date timeStamp;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message, Date timeStamp) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
