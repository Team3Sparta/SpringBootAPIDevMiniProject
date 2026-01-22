package com.sparta.services;

import com.sparta.dtos.TraineeDTO;
import com.sparta.dtos.TraineeMapper;
import com.sparta.entities.Trainee;

import com.sparta.repositories.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository, TraineeMapper traineeMapper) {
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
    }

    public List<TraineeDTO> getAllTrainees() {
        return traineeRepository.findAll().stream().map(traineeMapper::toDTO).toList();
    }

    public TraineeDTO getTraineeById(int id) {
        return traineeMapper.toDTO(traineeRepository.findById(id).orElse(null));
    }

    public TraineeDTO createTrainee(TraineeDTO traineeDTO) {
        if (traineeDTO == null) {
            throw new IllegalArgumentException("Trainee cannot be null");
        }
        return traineeMapper.toDTO(traineeRepository.save(traineeMapper.toEntity(traineeDTO)));
    }

    public boolean deleteTrainee(int id) {
        if (traineeRepository.existsById(id)) {
            traineeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TraineeDTO saveTrainee(TraineeDTO traineeDto) {

        Trainee entity = traineeMapper.toEntity(traineeDto);
        return traineeMapper.toDTO(traineeRepository.save(entity));
    }

    public TraineeDTO updateTrainee(TraineeDTO traineeDTO) {
        if (traineeRepository.existsById(traineeDTO.getId())) {
            Trainee traineeEntity = traineeMapper.toEntity(traineeDTO);
            Trainee updatedTrainee = traineeRepository.save(traineeEntity);
            return traineeMapper.toDTO(updatedTrainee);
        }
        throw new IllegalArgumentException("Trainee cannot be null");
    }

}
