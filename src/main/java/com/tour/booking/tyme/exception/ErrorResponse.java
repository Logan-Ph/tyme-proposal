package com.tour.booking.tyme.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private int status;
    private List<String> errors;

    public ErrorResponse(List<String> errors) {
        this.timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new java.util.Date());
        this.status = HttpStatus.BAD_REQUEST.value();
        this.errors = errors;
    }

    public String getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public List<String> getErrors() { return errors; }
}