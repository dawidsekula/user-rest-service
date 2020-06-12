package site.transcendence.userrestservice.api.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import site.transcendence.userrestservice.api.requests.UserCreateRequest;
import site.transcendence.userrestservice.api.roles.RoleMapper;

/**
 * UserMapper is an interface used by MapStruct
 * Mapper is created in runtime and provides simple implementation of mapping methods
 *
 * @see <a href="https://mapstruct.org/documentation/stable/reference/html/">MapStruct Documentation</a>
 */

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {RoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /*
    Mapping between UserDTO and UserEntity
     */
    UserEntity toEntity(UserDTO source);
    UserDTO toDTO(UserEntity source);

    void copy(UserEntity source, @MappingTarget UserDTO target);
    void copy(UserDTO source, @MappingTarget UserEntity target);

    /*
    Mapping between UserCreateRequest and UserEntity
     */
    UserEntity toEntity(UserCreateRequest request);


}
