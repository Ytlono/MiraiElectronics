package com.example.MiraiElectronics.Mapper;

import com.example.MiraiElectronics.dto.BrandDTO;
import com.example.MiraiElectronics.repository.realization.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "brandId",ignore = true)
    void updateBrand(BrandDTO brandDTO, @MappingTarget Brand brand);
}
