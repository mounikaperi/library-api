package com.knowledgedivers.libraryapi.dao;

import com.knowledgedivers.libraryapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUserEmail(String userEmail);
}
