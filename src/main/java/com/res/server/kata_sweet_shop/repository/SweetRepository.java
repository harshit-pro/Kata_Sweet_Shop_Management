package com.res.server.kata_sweet_shop.repository;

import com.res.server.kata_sweet_shop.entity.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface SweetRepository extends JpaRepository<Sweet, Long> {
    List<Sweet> findByNameContainingIgnoreCase(String name);
    List<Sweet>findByCategoryIgnoreCase(String category);

    // find by price less than or equal to a given value
    @Query("select s from Sweet s where s.price between :min and :max")
    List<Sweet>findByPriceRange(@Param("min")BigDecimal min, @Param("max")BigDecimal max);

}
