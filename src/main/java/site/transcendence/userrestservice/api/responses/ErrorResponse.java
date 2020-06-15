package site.transcendence.userrestservice.api.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss@z")
    private Date timestamp;
    private HttpStatus status;
    private String exception;
    private String message;
    private List<SubErrorResponse> subErrors;

}
