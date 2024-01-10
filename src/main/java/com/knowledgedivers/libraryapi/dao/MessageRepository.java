package com.knowledgedivers.libraryapi.dao;

import com.knowledgedivers.libraryapi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
