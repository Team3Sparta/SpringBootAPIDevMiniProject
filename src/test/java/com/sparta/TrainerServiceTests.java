package com.sparta;
import com.sparta.dtos.TrainerDTO;
import com.sparta.dtos.TrainerMapper;
import com.sparta.entities.Trainer;
import com.sparta.repositories.TrainerRepository;
import com.sparta.services.TrainerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TrainerServiceTests {

    private final TrainerRepository mockRepository = Mockito.mock(TrainerRepository.class);
    private final TrainerMapper mockMapper = Mockito.mock(TrainerMapper.class);
    private final TrainerService sut = new TrainerService(mockRepository, mockMapper);


    @Test
    @DisplayName("Ensure TrainerService is constructed correctly")
    public void constructServiceTest() {
        Assertions.assertInstanceOf(TrainerService.class, sut);
    }

    @Test
    @DisplayName("Constructor should throw exception with null repository or mapper")
    public void constructorWithNullRepositoryOrMapperTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TrainerService(null, mockMapper));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TrainerService(mockRepository, null));
    }


    @Test
    @DisplayName("Get All Trainers Test")
    public void getAllTrainersTest() {

        Trainer trainer1 = new Trainer();
        trainer1.setId(1);
        Trainer trainer2 = new Trainer();
        trainer2.setId(2);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer1);
        trainers.add(trainer2);

        Mockito.when(mockRepository.findAll()).thenReturn(trainers);

        TrainerDTO dto1 = new TrainerDTO();
        dto1.setId(1);
        TrainerDTO dto2 = new TrainerDTO();
        dto2.setId(2);

        Mockito.when(mockMapper.toDTO(trainer1)).thenReturn(dto1);
        Mockito.when(mockMapper.toDTO(trainer2)).thenReturn(dto2);


        List<TrainerDTO> result = sut.getAllTrainers();


        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals(2, result.get(1).getId());
    }


    @Test
    @DisplayName("Get Trainer Happy Path")
    public void getTrainerByIdTest() {
        Trainer trainer = new Trainer();
        trainer.setId(1);

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(trainer));

        TrainerDTO dto = new TrainerDTO();
        dto.setId(1);

        Mockito.when(mockMapper.toDTO(trainer)).thenReturn(dto);

        TrainerDTO result = sut.getTrainerById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Get Trainer Sad Path")
    public void getTrainerByIdUnhappyPathTest() {
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> sut.getTrainerById(1));
    }


    @Test
    @DisplayName("Create Trainer Happy Path")
    public void createTrainerHappyPathTest() {
        // Prepare DTO input
        TrainerDTO dto = new TrainerDTO();
        dto.setId(1);
        dto.setFirstName("Khurrum");

        Trainer trainerEntity = new Trainer();
        trainerEntity.setId(1);
        trainerEntity.setFirstName("Khurrum");

        Mockito.when(mockMapper.toEntity(dto)).thenReturn(trainerEntity);

        Mockito.when(mockRepository.save(trainerEntity)).thenReturn(trainerEntity);

        Mockito.when(mockMapper.toDTO(trainerEntity)).thenReturn(dto);

        TrainerDTO result = sut.createTrainer(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("Khurrum", result.getFirstName());
    }

    @Test
    @DisplayName("Create Trainer Null Argument")
    public void createTrainerNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.createTrainer(null));
    }

    @Test
    @DisplayName("Delete Trainer Happy Path")
    public void deleteTrainerHappyPathTest() {
        Mockito.when(mockRepository.existsById(1)).thenReturn(true);

        boolean result = sut.deleteTrainer(1);

        Assertions.assertTrue(result);
        Mockito.verify(mockRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Delete Trainer Sad Path")
    public void deleteTrainerUnhappyPathTest() {
        Mockito.when(mockRepository.existsById(1)).thenReturn(false);

        boolean result = sut.deleteTrainer(1);

        Assertions.assertFalse(result);
        Mockito.verify(mockRepository, Mockito.never()).deleteById(1);
    }

    @Test
    @DisplayName("Update Trainer Happy Path")
    public void updateTrainerHappyPathTest() {
        TrainerDTO dto = new TrainerDTO();
        dto.setId(1);
        dto.setFirstName("Mohammed");

        Trainer trainerEntity = new Trainer();
        trainerEntity.setId(1);
        trainerEntity.setFirstName("Mohammed");

        Mockito.when(mockMapper.toEntity(dto)).thenReturn(trainerEntity);

        Mockito.when(mockRepository.existsById(1)).thenReturn(true);
        Mockito.when(mockRepository.save(trainerEntity)).thenReturn(trainerEntity);

        Mockito.when(mockMapper.toDTO(trainerEntity)).thenReturn(dto);

        TrainerDTO result = sut.updateTrainer(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("Mohammed", result.getFirstName());
    }
    @Test
    @DisplayName("Update Trainer Sad Path")
    public void updateTrainerUnhappyPathTest() {
        TrainerDTO dto = new TrainerDTO();
        dto.setId(1);
        dto.setFirstName("Younis");

        Mockito.when(mockRepository.existsById(1)).thenReturn(false);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.updateTrainer(dto));
    }

    @Test
    @DisplayName("Test correct parameter passed to save")
    public void testCorrectParameterPassed() {

        TrainerDTO dto = new TrainerDTO();
        dto.setId(3);
        dto.setFirstName("Younis");
        Trainer trainerEntity = new Trainer();
        trainerEntity.setId(3);
        trainerEntity.setFirstName("Younis");
        Mockito.when(mockMapper.toEntity(dto)).thenReturn(trainerEntity);
        Mockito.when(mockRepository.save(trainerEntity)).thenReturn(trainerEntity);
        Mockito.when(mockMapper.toDTO(trainerEntity)).thenReturn(dto);
        sut.createTrainer(dto);


        Mockito.verify(mockRepository).save(trainerEntity);
        Mockito.verifyNoMoreInteractions(mockRepository);
    }}