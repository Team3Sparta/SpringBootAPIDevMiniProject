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

    public TraineeDTO createTrainee(Trainee trainee) {
        if (trainee == null) {
            throw new IllegalArgumentException("Trainee cannot be null");
        }
        return traineeMapper.toDTO(traineeRepository.save(trainee));
    }

    public boolean deleteTrainee(int id) {
        if (traineeRepository.existsById(id)) {
            traineeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TraineeDTO updateTrainee(Trainee trainee) {
        if (traineeRepository.existsById(trainee.getId())) {
            return traineeMapper.toDTO(traineeRepository.save(trainee));
        }
        throw new IllegalArgumentException("Trainee cannot be null");
    }

}
