package site.transcendence.userrestservice.api.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SubErrorResponse {

    private String message;
    private String field;
    private String value;

}
