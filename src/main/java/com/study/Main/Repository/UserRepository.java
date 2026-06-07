package com.study.Main.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.Main.Model.User;

//repository/UserRepository.java
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	boolean existsByUserName(String userName); // for duplicate check

	List<User> findByPlanType(String planType);
}
