package com.sparta.dtos;

import com.sparta.entities.Trainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeDTO toDTO(Trainee trainee);
    Trainee toEntity(TrainerDTO trainerDTO);
}
