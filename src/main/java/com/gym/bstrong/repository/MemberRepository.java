package com.gym.bstrong.repository;

import com.gym.bstrong.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    List<Member> findAll();

    @Query("SELECT m FROM Member m WHERE " +
            "(:firstName IS NULL OR m.firstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR m.lastName LIKE %:lastName%) AND " +
            "(:active IS NULL OR m.active = :active)")
    List<Member> findByFilters(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("active") Boolean active
    );

}
