package com.jb.common_template.repository;
import com.jb.common_template.entity.CommonTemplate;
import com.jb.common_template.entity.CommonTemplate.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommonTemplateRepository extends JpaRepository<CommonTemplate, Long> 
{
    // 🔹 Find by individual fields
    Optional<CommonTemplate> findByName(String name);
    Optional<CommonTemplate> findByAge(int age);
    Optional<CommonTemplate> findByActive(boolean active);
    Optional<CommonTemplate> findByCreatedDate(Date createdDate);
    Optional<CommonTemplate> findBySalary(Double salary);
    Optional<CommonTemplate> findByRating(Float rating);
    Optional<CommonTemplate> findByAmount(BigDecimal amount);
    Optional<CommonTemplate> findByLocalDate(LocalDate localDate);
    Optional<CommonTemplate> findByLocalTime(LocalTime localTime);
    Optional<CommonTemplate> findByLocalDateTime(LocalDateTime localDateTime);
    Optional<CommonTemplate> findByVerified(Boolean verified);
    Optional<CommonTemplate> findByStatus(Status status);

    // 🔹 List return types for multiple results
    List<CommonTemplate> findAllByName(String name);
    List<CommonTemplate> findAllByActive(boolean active);
    List<CommonTemplate> findAllByStatus(Status status);
    List<CommonTemplate> findAllByAgeGreaterThan(int age);
    List<CommonTemplate> findAllBySalaryBetween(Double min, Double max);
    List<CommonTemplate> findAllByCreatedDateAfter(Date date);
    List<CommonTemplate> findAllByLocalDateBefore(LocalDate date);

    // 🔹 Sorting
    List<CommonTemplate> findAllByOrderByNameAsc();
    List<CommonTemplate> findAllByOrderByCreatedDateDesc();

    // 🔹 Pagination support (use with Pageable in service/controller)
    // Page<CommonTemplate> findAll(Pageable pageable);

    // 🔹 Exists (boolean check)
    boolean existsByName(String name);
    boolean existsByStatus(Status status);

    // 🔹 Count by condition
    long countByActive(boolean active);
    long countByStatus(Status status);

    // 🔹 Delete by condition
    void deleteByName(String name);
    void deleteAllByStatus(Status status);

    // 🔹 Basic CRUD methods inherited from JpaRepository:
    // save(entity)
    // saveAll(entities)
    // findById(id)
    // findAll()
    // deleteById(id)
    // delete(entity)
    // deleteAll()
    // existsById(id)
    // count()
}
