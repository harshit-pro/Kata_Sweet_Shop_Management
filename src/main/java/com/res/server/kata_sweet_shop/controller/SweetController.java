package com.res.server.kata_sweet_shop.controller;

import com.res.server.kata_sweet_shop.dto.PurchaseRequest;
import com.res.server.kata_sweet_shop.dto.SweetRequest;
import com.res.server.kata_sweet_shop.dto.SweetResponse;
import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.service.ImageService;
import com.res.server.kata_sweet_shop.service.SweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sweets")
@RequiredArgsConstructor
public class SweetController {

    private final ImageService imageService;
    private final SweetService sweetService;
    @GetMapping("/all")
    public List<SweetResponse> all() {System.out.println("Fetching all sweets");
        return sweetService.listAll().stream().map(this::toResp).collect(Collectors.toList());
    }
    @GetMapping("/search")
    public List<SweetResponse> search(@RequestParam Optional<String> name,
                                      @RequestParam Optional<String> category,
                                      @RequestParam Optional<BigDecimal> minPrice,
                                      @RequestParam Optional<BigDecimal> maxPrice) {
        return sweetService.search(name, category, minPrice, maxPrice).stream().map(this::toResp).collect(Collectors.toList());
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SweetResponse> create(
            @RequestPart("sweet") @Valid SweetRequest req,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        String imageUrl = imageService.uploadImage(imageFile);
        req.setImageUrl(imageUrl);
        Sweet s = sweetService.create(req);
        return ResponseEntity.ok(toResp(s));
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SweetResponse> update(@PathVariable Long id, @RequestBody SweetRequest req) {
        System.out.println("Updating Sweet with ID: " + id);
        Sweet s = sweetService.update(id, req);
        return ResponseEntity.ok(toResp(s));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sweetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/purchase/{id}")
    @PreAuthorize("hasRole('USER')")   // yaha admin aur user dono kar sakte hain
    // but usually purchase user karta hai
    public ResponseEntity<?> purchase(@PathVariable Long id, @RequestBody PurchaseRequest request) {
        sweetService.purchasesweet(id, request.getQuantity());
        return ResponseEntity.ok("Purchased");
    }
    @PostMapping("/restock/{id}")
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
        response.setImageUrl(s.getImageUrl()); // Use getImageUrl() for consistency
        return response;
    }
}