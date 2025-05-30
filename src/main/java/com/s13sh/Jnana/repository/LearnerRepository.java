package com.s13sh.Jnana.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.Jnana.model.Learner;

public interface LearnerRepository extends JpaRepository<Learner, Long>{

	boolean existsByMobile(long mobile);

	boolean existsByEmail(String email);

	Learner findByEmail(String email);

}
