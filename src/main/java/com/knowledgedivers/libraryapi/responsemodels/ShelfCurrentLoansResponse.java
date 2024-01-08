package com.knowledgedivers.libraryapi.responsemodels;

import com.knowledgedivers.libraryapi.entity.Book;
import lombok.Data;
@Data
public class ShelfCurrentLoansResponse {
    public ShelfCurrentLoansResponse(Book book, int daysLeft) {
        this.book = book;
        this.daysLeft = daysLeft;
    }
    private Book book;
    private int daysLeft;
}
