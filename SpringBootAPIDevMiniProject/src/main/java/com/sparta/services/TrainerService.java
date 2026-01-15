package com.sparta.services;

import com.sparta.dtos.TrainerDTO;
import com.sparta.dtos.TrainerMapper;
import com.sparta.entities.Trainer;
import com.sparta.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class
TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        if (trainerRepository == null || trainerMapper == null) {
            throw new IllegalArgumentException("Repository and Mapper cannot be null");
        }
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    public List<TrainerDTO> getAllTrainers() {
        return trainerRepository.findAll()
                .stream()
                .map(trainerMapper::toDTO)
                .toList();
    }

    public TrainerDTO getTrainerById(int id) {
        return trainerRepository.findById(id)
                .map(trainerMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Trainer not found"));
    }

    public TrainerDTO createTrainer(TrainerDTO trainerDTO) {
        if (trainerDTO == null) {
            throw new IllegalArgumentException("Trainer cannot be null");
        }
        Trainer trainerEntity = trainerMapper.toEntity(trainerDTO);
        Trainer savedTrainer = trainerRepository.save(trainerEntity);
        return trainerMapper.toDTO(savedTrainer);
    }

    public boolean deleteTrainer(int id) {
        if (trainerRepository.existsById(id)) {
            trainerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TrainerDTO updateTrainer(TrainerDTO trainerDTO) {
        if (trainerRepository.existsById(trainerDTO.getId())) {
            Trainer trainerEntity = trainerMapper.toEntity(trainerDTO);
            Trainer updatedTrainer = trainerRepository.save(trainerEntity);
            return trainerMapper.toDTO(updatedTrainer);
        } else {
            throw new IllegalArgumentException("Trainer with ID " + trainerDTO.getId() + " does not exist.");
        }
    }
}
