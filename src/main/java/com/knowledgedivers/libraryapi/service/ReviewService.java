package com.knowledgedivers.libraryapi.service;

import com.knowledgedivers.libraryapi.dao.BookRepository;
import com.knowledgedivers.libraryapi.dao.ReviewRepository;
import com.knowledgedivers.libraryapi.entity.Review;
import com.knowledgedivers.libraryapi.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;
    public ReviewService() {};
    @Autowired
    public ReviewService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }
    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
        if (validateReview != null ) {
            throw new Exception("Review already created");
        }
        Review review = new Review();
        review.setBookId((reviewRequest.getBookId()));
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(
                    reviewRequest.getReviewDescription().map(Object::toString).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }
}
