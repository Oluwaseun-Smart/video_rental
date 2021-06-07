package com.oos.rental.repositories;

import com.oos.rental.entities.Price;
import com.oos.rental.models.VideoType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByVideoType(String videoType);

    Page<Price> findAll(Pageable pageable);
}
