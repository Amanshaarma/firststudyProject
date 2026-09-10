package com.tms.Main.Repository;

import com.tms.Main.Model.LrBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LrBookRepository extends JpaRepository<LrBook, Long> {

    List<LrBook> findByTripTripId(Long tripId);

    List<LrBook> findByCompanyId(Long companyId);
}
