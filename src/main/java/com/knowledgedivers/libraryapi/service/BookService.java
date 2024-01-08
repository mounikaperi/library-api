package com.knowledgedivers.libraryapi.service;

import java.lang.*;
import com.knowledgedivers.libraryapi.dao.BookRepository;
import com.knowledgedivers.libraryapi.dao.CheckoutRepository;
import com.knowledgedivers.libraryapi.entity.Book;
import com.knowledgedivers.libraryapi.entity.Checkout;
import com.knowledgedivers.libraryapi.responsemodels.ShelfCurrentLoansResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;
    public BookService() {}
    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }
    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (!book.isPresent() || validateCheckout!= null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
        bookRepository.save(book.get());
        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );
        checkoutRepository.save(checkout);
        return book.get();
    }
    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validateCheckout != null;
    }
    public int currentLoanCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }
    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponse = new ArrayList<>();
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();
        for (Checkout i: checkoutList) {
            bookIdList.add(i.getBookId());
        }
        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Book book: books) {
            Optional<Checkout> checkout = checkoutList.stream().filter(currentBook -> Objects.equals(currentBook.getBookId(), book.getId())).findFirst();
            if (checkout.isPresent()) {
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());
                TimeUnit time = TimeUnit.DAYS;
                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
                shelfCurrentLoansResponse.add(new ShelfCurrentLoansResponse(book, (int) difference_In_Time));
            }
        }
        return shelfCurrentLoansResponse;
    }
}
