package com.brageast.project.webmessage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage<T> implements Serializable {

//    private String path;
    @Builder.Default
    private int code = HttpStatus.OK.value();
    @Builder.Default
    private String message = HttpStatus.OK.getReasonPhrase();
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    public static <E> ResponseMessageBuilder<E> of(HttpStatus status) {
        return (ResponseMessageBuilder<E>) ResponseMessage
                .builder()
                .code(status.value())
                .message(status.getReasonPhrase());
    }

    public static <E> ResponseMessage<E> ok(Object data) {
        return (ResponseMessage<E>) ResponseMessage
                .builder()
                .data(data)
                .build();
    }

}
