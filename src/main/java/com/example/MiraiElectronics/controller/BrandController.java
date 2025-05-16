package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.BrandDTO;
import com.example.MiraiElectronics.service.BrandService;
import com.example.MiraiElectronics.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("api/brand")
public class BrandController extends BaseController{

    private final BrandService brandService;

    public BrandController(SessionService sessionService, BrandService brandService) {
        super(sessionService);
        this.brandService = brandService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Long id){
        return ResponseEntity.ok(
                brandService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllBrands(){
        return ResponseEntity.ok(
                brandService.findAll()
        );
    }

    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody BrandDTO brandDTO){
        return ResponseEntity.ok(
                brandService.save(
                        brandService.toEntity(brandDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable Long id){
        brandService.deleteById(id);
        return ResponseEntity.ok().body("deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO){
        return ResponseEntity.ok(
                brandService.updateBrand(id,brandDTO)
        );
    }
}
