package com.gym.bstrong.repository;

import com.gym.bstrong.domain.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findAll();

    @Query("SELECT b FROM Booking b WHERE " +
            "(:attended IS NULL OR b.attended = :attended) AND " +
            "(:memberId IS NULL OR b.member.id = :memberId) AND " +
            "(:activityId IS NULL OR b.activity.id = :activityId)")
    List<Booking> findByFilters(
            @Param("attended") Boolean attended,
            @Param("memberId") Long memberId,
            @Param("activityId") Long activityId
    );
}
