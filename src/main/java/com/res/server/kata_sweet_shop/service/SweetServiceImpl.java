package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.SweetRequest;
import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.exception.InsufficientStockException;
import com.res.server.kata_sweet_shop.exception.ResourceNotFoundException;
import com.res.server.kata_sweet_shop.repository.SweetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SweetServiceImpl implements SweetService{

    private final SweetRepository sweetRepository;
    private final SweetRequestValidator validator;

    /**
     * Creates a new Sweet entity from the given request.
     * Validates all business rules before saving to database.
     * 
     * @param request The sweet creation request with all necessary data
     * @return The created Sweet entity
     * @throws IllegalArgumentException if validation fails
     */
    @Override
    public Sweet create(SweetRequest request) {
        validator.validate(request);
        
        Sweet sweet = Sweet.builder()
               .name(request.getName())
               .category(request.getCategory())
               .price(request.getPrice())
               .quantity(request.getQuantity())
               .imageUrl(request.getImageUrl())
               .build();
        
        return sweetRepository.save(sweet);
    }

/**
 * Updates an existing Sweet entity by modifying its fields and saving.
 * Following clean code principles - preserving the existing ID and DB-managed fields.
 * 
 * @param id The ID of the sweet to update
 * @param request The update request with new data
 * @return The updated Sweet entity
 */
@Override
public Sweet update(Long id, SweetRequest request) {
    validator.validate(request);
    
    Sweet sweet = getSweetById(id);
    sweet.setName(request.getName());
    sweet.setCategory(request.getCategory());
    sweet.setPrice(request.getPrice());
    sweet.setQuantity(request.getQuantity());
    sweet.setImageUrl(request.getImageUrl());

    if (request.getQuantity() != null) {
        sweet.setQuantity(request.getQuantity());
    }
    return sweetRepository.save(sweet);
}
    /**
     * Retrieves a Sweet entity by its ID.
     * 
     * @param id The ID of the sweet to retrieve
     * @return The Sweet entity
     * @throws ResourceNotFoundException if sweet not found
     */
    private Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found"));
    }
    
    /**
     * Deletes a sweet by its ID.
     * 
     * @param id The ID of the sweet to delete
     */
    public void delete(Long id) {
        sweetRepository.deleteById(id);
    }
    
    /**
     * Retrieves all sweets from the database.
     * 
     * @return List of all Sweet entities
     */
    public List<Sweet> listAll() {
        return sweetRepository.findAll();
    }
    /**
     * Searches for sweets based on various criteria.
     * 
     * @param name Optional name filter (case-insensitive partial match)
     * @param category Optional category filter (case-insensitive exact match)
     * @param minPrice Optional minimum price filter
     * @param maxPrice Optional maximum price filter
     * @return List of Sweet entities matching the criteria
     */
    @Override
    public List<Sweet> search(Optional<String> name, Optional<String> category, Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice) {
        if (name.isPresent()) return sweetRepository.findByNameContainingIgnoreCase(name.get());
        if (category.isPresent()) return sweetRepository.findByCategoryIgnoreCase(category.get());
        if (minPrice.isPresent() && maxPrice.isPresent()) return sweetRepository.findByPriceRange(minPrice.get(), maxPrice.get());
        return sweetRepository.findAll();
    }
    
    /**
     * Processes a sweet purchase by reducing the quantity in stock.
     * Validates sufficient stock is available before processing.
     * 
     * @param id The ID of the sweet to purchase
     * @param qty The quantity to purchase
     * @throws ResourceNotFoundException if sweet not found
     * @throws InsufficientStockException if insufficient stock available
     */
    @Override
    @Transactional
    public void purchasesweet(Long id, int qty) {
        Sweet s = sweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sweet not found"));
        if (s.getQuantity() < qty) {
            throw new InsufficientStockException(
                    String.format("Requested %d but only %d available", qty, s.getQuantity())
            );
        }
        s.setQuantity(s.getQuantity() - qty);
        sweetRepository.save(s);
    }
    
    /**
     * Restocks a sweet by adding to the current quantity.
     * 
     * @param id The ID of the sweet to restock
     * @param qty The quantity to add to stock
     * @throws ResourceNotFoundException if sweet not found
     */
    @Override
    @Transactional
    public void restock(Long id, int qty) {
        Sweet s = sweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sweet not found"));
        s.setQuantity(s.getQuantity() + qty);
        sweetRepository.save(s);
    }
    
    /**
     * Finds a sweet by its ID.
     * 
     * @param id The ID of the sweet to find
     * @return Optional containing the Sweet if found, empty otherwise
     */
    @Override
    public Optional<Sweet> findById(Long id) {
        return sweetRepository.findById(id);
    }
}
