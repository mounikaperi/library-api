package com.knowledgedivers.libraryapi.dao;

import com.knowledgedivers.libraryapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
