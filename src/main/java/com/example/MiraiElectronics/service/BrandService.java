package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.Mapper.BrandMapper;
import com.example.MiraiElectronics.dto.BrandDTO;
import com.example.MiraiElectronics.repository.BrandRepository;
import com.example.MiraiElectronics.repository.realization.Brand;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.stereotype.Service;

@Service
public class BrandService extends GenericEntityService<Brand, Long> {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository,BrandMapper brandMapper) {
        super(brandRepository);
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public Brand toEntity(BrandDTO dto) {
        return Brand.builder()
                .name(dto.getName())
                .country(dto.getCountry())
                .description(dto.getDescription())
                .build();
    }

    public Brand updateBrand(Long id,BrandDTO brandDTO){
        Brand brand = findById(id);
        brandMapper.updateBrand(brandDTO,brand);
        return brand;
    }
}
