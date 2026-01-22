package com.sparta.dtos;

import com.sparta.entities.Trainee;
import org.mapstruct.Mapper;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeDTO toDTO(Trainee trainee);
    Trainee toEntity(TraineeDTO traineeDTO);
}
