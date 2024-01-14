package com.knowledgedivers.libraryapi.service;

import com.knowledgedivers.libraryapi.dao.BookRepository;
import com.knowledgedivers.libraryapi.entity.Book;
import com.knowledgedivers.libraryapi.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
    private BookRepository bookRepository;

    @Autowired
    public AdminService (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopiesAvailable());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }
}