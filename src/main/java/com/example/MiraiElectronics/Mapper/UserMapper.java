package com.example.MiraiElectronics.Mapper;

import com.example.MiraiElectronics.dto.UpdateUserDataDTO;
import com.example.MiraiElectronics.dto.AddressDTO;
import com.example.MiraiElectronics.repository.realization.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressToString")
    void updateUserFromDto(UpdateUserDataDTO dto, @MappingTarget User user);

    @Named("addressToString")
    default String mapAddressToString(AddressDTO address) {
        return address == null ? null : address.toString();
    }
}
