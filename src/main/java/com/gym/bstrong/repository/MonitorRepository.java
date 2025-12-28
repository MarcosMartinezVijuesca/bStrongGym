package com.gym.bstrong.repository;

import com.gym.bstrong.domain.Monitor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRepository extends CrudRepository<Monitor, Long> {

    List<Monitor> findAll();


    @Query("SELECT m FROM Monitor m WHERE " +
            "(:name IS NULL OR m.name LIKE %:name%) AND " +
            "(:specialty IS NULL OR m.specialty LIKE %:specialty%) AND " +
            "(:available IS NULL OR m.available = :available)")
    List<Monitor> findByFilters(
            @Param("name") String name,
            @Param("specialty") String specialty,
            @Param("available") Boolean available
    );
}