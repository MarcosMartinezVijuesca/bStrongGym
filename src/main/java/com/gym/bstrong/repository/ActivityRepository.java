package com.gym.bstrong.repository;

import com.gym.bstrong.domain.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    List<Activity> findAll();

    @Query("SELECT a FROM Activity a WHERE " +
            "(:name IS NULL OR a.name LIKE %:name%) AND " +
            "(:active IS NULL OR a.active = :active) AND " +
            "(:minCapacity IS NULL OR a.capacity >= :minCapacity)")
    List<Activity> findByFilters(
            @Param("name") String name,
            @Param("active") Boolean active,
            @Param("minCapacity") Integer minCapacity
    );
}