package com.knowledgedivers.libraryapi.requestmodels;

import lombok.Data;

@Data
public class AddBookRequest {
    private String title;
    private String author;
    private String description;
    private int copies;
    private int copiesAvailable;
    private String category;
    private String img;
}
