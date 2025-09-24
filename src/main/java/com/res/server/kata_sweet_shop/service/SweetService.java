package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.SweetRequest;
import com.res.server.kata_sweet_shop.entity.Sweet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface SweetService{
    Sweet create(SweetRequest sweetRequest);
    Sweet update(Long id,SweetRequest sweetRequest);

    void delete(Long id);
    List<Sweet> listAll();

    List<Sweet> search(Optional<String> name, Optional<String>Category, Optional<BigDecimal> minPrice,
                       Optional<BigDecimal> maxPrice);
    void purchase(Long id,int qty);
    void restock(Long id,int qty);
    Optional<Sweet> findById(Long id);


}
