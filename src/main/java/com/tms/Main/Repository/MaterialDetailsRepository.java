package com.tms.Main.Repository;

import com.tms.Main.Model.MaterialDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialDetailsRepository
        extends JpaRepository<MaterialDetails, Long> {

    List<MaterialDetails> findByCompanyId(Long companyId);
}
