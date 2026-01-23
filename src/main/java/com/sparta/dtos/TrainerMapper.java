package com.sparta.dtos;
import com.sparta.entities.Trainer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    TrainerDTO toDTO(Trainer trainer);
    Trainer toEntity(TrainerDTO trainerDTO);
}
