package com.brageast.project.webmessage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage implements Serializable {

//    private String path;
    @Builder.Default
    private int code = HttpStatus.OK.value();
    @Builder.Default
    private String message = HttpStatus.OK.getReasonPhrase();
    private Object data;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    public static ResponseMessageBuilder of(HttpStatus status) {
        return ResponseMessage
                .builder()
                .code(status.value())
                .message(status.getReasonPhrase());
    }

}
