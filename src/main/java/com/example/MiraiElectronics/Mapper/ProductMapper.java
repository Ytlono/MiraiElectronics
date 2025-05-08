package com.example.MiraiElectronics.Mapper;

import com.example.MiraiElectronics.repository.realization.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    void updateProduct(Product source, @MappingTarget Product target);
}

