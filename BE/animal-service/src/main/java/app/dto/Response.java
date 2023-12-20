package app.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String status;
    private String message;
}
