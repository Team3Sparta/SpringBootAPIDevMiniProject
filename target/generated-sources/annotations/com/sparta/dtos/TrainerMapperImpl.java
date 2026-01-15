package com.sparta.dtos;

import com.sparta.entities.Trainer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-14T14:52:15+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class TrainerMapperImpl implements TrainerMapper {

    @Override
    public TrainerDTO toDTO(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }

        TrainerDTO trainerDTO = new TrainerDTO();

        trainerDTO.setId( trainer.getId() );
        trainerDTO.setFirstName( trainer.getFirstName() );
        trainerDTO.setLastName( trainer.getLastName() );

        return trainerDTO;
    }

    @Override
    public Trainer toEntity(TrainerDTO trainerDTO) {
        if ( trainerDTO == null ) {
            return null;
        }

        Trainer trainer = new Trainer();

        trainer.setId( trainerDTO.getId() );
        trainer.setFirstName( trainerDTO.getFirstName() );
        trainer.setLastName( trainerDTO.getLastName() );

        return trainer;
    }
}
