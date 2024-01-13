package com.knowledgedivers.libraryapi.requestmodels;

import lombok.Data;

@Data
public class AdminQuestionRequest {
    private Long id;
    private String user_email;
    private String response;
}
