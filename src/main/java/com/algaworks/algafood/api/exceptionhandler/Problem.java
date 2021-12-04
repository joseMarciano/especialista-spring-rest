package com.algaworks.algafood.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {


    private Integer status;
    private String type;
    private String title;
    private String detail;

    private OffsetDateTime timestamp;
    private String userMessage;

    List<Field> fields = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }

}
