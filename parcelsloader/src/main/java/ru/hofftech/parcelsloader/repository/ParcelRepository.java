package ru.hofftech.parcelsloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hofftech.parcelsloader.model.entity.ParcelEntity;

import java.util.Optional;
import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {

    Optional<ParcelEntity> findByName(String name);

    @Query(value = "SELECT * FROM parcel LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<ParcelEntity> findAllWithLimitAndOffset(@Param("limit") Long limit,
                                                 @Param("offset") Long offset);
}
