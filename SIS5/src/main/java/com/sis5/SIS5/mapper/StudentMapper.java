package com.sis5.SIS5.mapper;

import com.sis5.SIS5.model.dto.request.StudentRequestDTO;
import com.sis5.SIS5.model.dto.response.StudentResponseDTO;
import com.sis5.SIS5.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Student toEntity(StudentRequestDTO requestDTO);

    StudentResponseDTO toResponseDTO(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDTO(StudentRequestDTO requestDTO, @MappingTarget Student student);
}