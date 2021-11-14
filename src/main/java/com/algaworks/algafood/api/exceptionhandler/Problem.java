package com.algaworks.algafood.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {


    private Integer status;
    private String type;
    private String title;
    private String detail;

    private LocalDateTime timestamp;

}