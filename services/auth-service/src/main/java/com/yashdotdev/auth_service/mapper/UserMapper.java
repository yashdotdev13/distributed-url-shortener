package com.yashdotdev.auth_service.mapper;


import com.yashdotdev.auth_service.dtos.request.RegisterRequest;
import com.yashdotdev.auth_service.dtos.response.UserResponse;
import com.yashdotdev.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "accountStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);

}