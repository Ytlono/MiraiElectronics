package com.example.MiraiElectronics.Mapper;

import com.example.MiraiElectronics.dto.AddressDTO;
import com.example.MiraiElectronics.dto.UpdateUserDataDTO;
import com.example.MiraiElectronics.repository.realization.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "phone", source = "phoneNumber")  // Прокси маппинг для phoneNumber
    @Mapping(target = "address", source = "address", qualifiedByName = "addressToString")  // Прокси маппинг для address
    void updateUserFromDto(UpdateUserDataDTO dto, @MappingTarget User user);

    @Named("addressToString")
    default String mapAddressToString(AddressDTO address) {
        return address == null ? null : address.toString();  // Преобразование объекта address в строку
    }
}
