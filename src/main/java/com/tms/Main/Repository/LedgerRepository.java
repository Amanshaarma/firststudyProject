package com.tms.Main.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tms.Main.Model.Ledger;
import com.tms.Main.enumData.GroupType;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {

 // Duplicate check
 boolean existsByLedgerNameAndCompanyProfileCompanyId(
         String ledgerName, Long companyId);

 // Child check for delete
 boolean existsByGroupGroupId(Long groupId);

 // Get all by company
 Page<Ledger> findByCompanyProfileCompanyId(
         Long companyId, Pageable pageable);

 // Get all by company and group_type
 @Query("SELECT l FROM Ledger l " +
        "JOIN l.group g " +
        "WHERE l.companyProfile.companyId = :companyId " +
        "AND g.groupType IN :groupTypes")
 Page<Ledger> findByCompanyIdAndGroupTypes(
         @Param("companyId") Long companyId,
         @Param("groupTypes") List<GroupType> groupTypes,
         Pageable pageable);

 // Get all by group_type only
 @Query("SELECT l FROM Ledger l " +
        "JOIN l.group g " +
        "WHERE g.groupType IN :groupTypes")
 Page<Ledger> findByGroupTypes(
         @Param("groupTypes") List<GroupType> groupTypes,
         Pageable pageable);

 // Selected columns only (summary)
 @Query("SELECT l FROM Ledger l " +
        "WHERE l.companyProfile.companyId = :companyId")
 Page<Ledger> findSummaryByCompanyId(
         @Param("companyId") Long companyId,
         Pageable pageable);
}