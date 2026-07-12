package com.study.Main.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.Main.Model.CompanyProfiles;

public interface CompanyRepository  extends JpaRepository<CompanyProfiles, Long> {

    Optional<CompanyProfiles> findByEmailId(String emailId);
    Optional<CompanyProfiles> findByMobileNo(String mobileNo);
    Optional<CompanyProfiles> findByPanNumber(String panNumber);
    Optional<CompanyProfiles> findByGstNo(String gstNo);
    List<CompanyProfiles> findByUserUserId(Long userId);
}