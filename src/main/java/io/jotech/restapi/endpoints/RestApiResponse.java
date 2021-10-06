package io.jotech.restapi.endpoints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestApiResponse{
    private boolean success;
    private String msg;
    private Object data;
    private long totalCount;
}
