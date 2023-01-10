package com.example.licenta.repository;

import com.example.licenta.model.GlobalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalDetailsRepository extends JpaRepository<GlobalDetails,Long> {
	GlobalDetails findFirstByOrderByIdDesc();
}
