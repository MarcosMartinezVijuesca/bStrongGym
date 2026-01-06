package com.gym.bstrong.repository;

import com.gym.bstrong.domain.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    List<Subscription> findAll();

    @Query("SELECT s FROM Subscription s WHERE " +
            "(:type IS NULL OR s.type = :type) AND " +
            "(:memberId IS NULL OR s.member.id = :memberId) AND " +
            "(:active IS NULL OR s.active = :active)")
    List<Subscription> findByFilters(
            @Param("type") String type,
            @Param("memberId") Long memberId,
            @Param("active") Boolean active
    );
}
