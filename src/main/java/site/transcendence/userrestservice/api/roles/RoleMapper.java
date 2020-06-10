package site.transcendence.userrestservice.api.roles;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    /*
    Mapping between RoleDTO and RoleEntity
     */
    RoleEntity toEntity(RoleDTO source);

    RoleDTO toDTO(RoleEntity source);

    void copy(RoleEntity source, @MappingTarget RoleDTO target);

    void copy(RoleDTO source, @MappingTarget RoleEntity target);

}
