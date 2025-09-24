package com.res.server.kata_sweet_shop.controller;

import com.res.server.kata_sweet_shop.dto.PurchaseRequest;
import com.res.server.kata_sweet_shop.dto.SweetRequest;
import com.res.server.kata_sweet_shop.dto.SweetResponse;
import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.service.SweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    private final SweetService sweetService;
    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }
    @GetMapping
    public List<SweetResponse> all() {
        return sweetService.listAll().stream().map(this::toResp).collect(Collectors.toList());
    }
    @GetMapping("/search")
    public List<SweetResponse> search(@RequestParam Optional<String> name,
                                      @RequestParam Optional<String> category,
                                      @RequestParam Optional<BigDecimal> minPrice,
                                      @RequestParam Optional<BigDecimal> maxPrice) {
        return sweetService.search(name, category, minPrice, maxPrice).stream().map(this::toResp).collect(Collectors.toList());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SweetResponse> create(@RequestBody SweetRequest req) {
        System.out.println("Creating a new Sweet");
        Sweet s = sweetService.create(req);
        return ResponseEntity.ok(toResp(s));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SweetResponse> update(@PathVariable Long id, @RequestBody SweetRequest req) {
        Sweet s = sweetService.update(id, req);
        return ResponseEntity.ok(toResp(s));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sweetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // <-- Fix: ensure only authenticated users can purchase
    public ResponseEntity<?> purchase(@PathVariable Long id, @RequestBody PurchaseRequest request) {
        sweetService.purchasesweet(id, request.getQuantity());
        return ResponseEntity.ok("Purchased");
    }

    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> restock(@PathVariable Long id, @RequestBody PurchaseRequest request) {
        sweetService.restock(id, request.getQuantity());
        return ResponseEntity.ok("Restocked");
    }

    private SweetResponse toResp(Sweet s) {
        SweetResponse response = new SweetResponse();
        response.setId(s.getId());
        response.setName(s.getName());
        response.setCategory(s.getCategory());
        response.setPrice(s.getPrice());
        response.setQuantity(s.getQuantity());
        return response;
    }
}