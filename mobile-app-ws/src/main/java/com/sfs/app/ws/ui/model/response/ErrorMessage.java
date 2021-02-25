package com.sfs.app.ws.ui.model.response;

import java.util.Date;

public class ErrorMessage {
    private Date timespamp;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(Date timespamp, String message) {
        this.timespamp = timespamp;
        this.message = message;
    }

    public Date getTimespamp() {
        return timespamp;
    }

    public void setTimespamp(Date timespamp) {
        this.timespamp = timespamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
