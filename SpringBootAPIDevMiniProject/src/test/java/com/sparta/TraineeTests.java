package com.sparta;



import com.sparta.dtos.TraineeDTO;
import com.sparta.dtos.TraineeMapper;

import com.sparta.entities.Trainee;
import com.sparta.repositories.TraineeRepository;
import com.sparta.services.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

public class TraineeTests {

    private final TraineeRepository mockRepository = Mockito.mock(TraineeRepository.class);
    private final TraineeMapper mockMapper = Mockito.mock(TraineeMapper.class);
    private final TraineeService sut = new TraineeService(mockRepository,mockMapper);

    static Trainee entity1, entity2;
    static TraineeDTO entityDto1, entityDto2;

    @BeforeAll
    public static void setUp() {

        entity1 = new Trainee();
        entity1.setFirstName("Mariusz");
        entity1.setLastName("B");
        entity1.setId(1);

        entity2 = new Trainee();
        entity2.setFirstName("Gregory");
        entity2.setLastName("C");
        entity2.setId(1);

        entityDto1 = new TraineeDTO();
        entityDto1.setFirstName("Mariusz");
        entityDto1.setLastName("B");
        entityDto1.setId(1);

        entityDto2 = new TraineeDTO();
        entityDto2.setFirstName("Gregory");
        entityDto2.setLastName("C");
        entityDto2.setId(1);
    }

    @Test
    @DisplayName("GET:(Happy) Get All Trainee Test")
    public void getAllTraineeListTest(){
        // Arrange
        List<Trainee> entitiesList = new ArrayList<>();

        entitiesList.add(entity1);
        entitiesList.add(entity2);

        Mockito.when(mockMapper.toDTO(entity1)).thenReturn(entityDto1);
        Mockito.when(mockMapper.toDTO(entity2)).thenReturn(entityDto2);
        Mockito.when(mockRepository.findAll()).thenReturn(entitiesList);
        // Act
        List<TraineeDTO> result = sut.getAllTrainees();
        //Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Mariusz", result.get(0).getFirstName());
        Assertions.assertEquals("B", result.get(0).getLastName());
        Assertions.assertEquals("Gregory", result.get(1).getFirstName());
        Assertions.assertEquals("C", result.get(1).getLastName());
    }

    @Test
    @DisplayName("GET:(HAPPY)-> Successfully retrieve Trainee by ID")
    void getTraineeById_IdExists_ReturnsTraineeDTO() {

        int testId = 1;
        Trainee trainee = new Trainee();
        trainee.setId(testId);
        trainee.setFirstName("Mariusz");
        trainee.setLastName("B");

        TraineeDTO expectedDto = new TraineeDTO();
        expectedDto.setId(testId);
        expectedDto.setFirstName("Mariusz");
        expectedDto.setLastName("B");

        Mockito.when(mockRepository.findById(testId))
                .thenReturn(Optional.of(trainee));

        Mockito.when(mockMapper.toDTO(trainee))
                .thenReturn(expectedDto);

        TraineeDTO result = sut.getTraineeById(testId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testId, result.getId());
        Assertions.assertEquals("Mariusz", result.getFirstName());
        Mockito.verify(mockRepository).findById(testId);
        Mockito.verify(mockMapper).toDTO(trainee);
    }


    @Test
    @DisplayName("GET:(SAD)-> Check if non-existent ID of trainee is retrieved")
    void checkIfNonExistentIdOfTraineeIsRetrievedTest() {
        // 1. Arrange
        int testId = 99;

        Mockito.when(mockRepository.findById(testId)).thenReturn(Optional.empty());
        Mockito.when(mockMapper.toDTO(null)).thenReturn(null);
        TraineeDTO result = sut.getTraineeById(testId);
        Assertions.assertNull(result, "The DTO should be null if the trainee wasn't found");
        Mockito.verify(mockRepository).findById(testId);
        Mockito.verify(mockMapper).toDTO(null);
    }



    @Test
    @DisplayName("GET:(Happy)-> Check if getTraineeById is called once")
    void checkFindByIdIsCalledOnceOnRepositoryTest() {
        Mockito.when(mockRepository.existsById(anyInt())).thenReturn(true);
        sut.getTraineeById(anyInt());
        Mockito.verify(mockRepository).findById(anyInt());
        Mockito.verify(mockRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("POST:(Happy)-> Check if new trainee is created - happy path")
    void checkCreationOfNewTraineeTest(){

        Trainee inputTrainee = new Trainee();
        inputTrainee.setFirstName("Maria");
        inputTrainee.setLastName("D");

        Trainee savedTrainee = new Trainee();
        savedTrainee.setId(3);
        savedTrainee.setFirstName("Maria");
        savedTrainee.setLastName("D");

        TraineeDTO expectedDto = new TraineeDTO();
        expectedDto.setFirstName("Maria");
        expectedDto.setLastName("D");

        Mockito.when(mockRepository.save(inputTrainee)).thenReturn(savedTrainee);
        Mockito.when(mockMapper.toDTO(savedTrainee)).thenReturn(expectedDto);

        TraineeDTO result = sut.createTrainee(inputTrainee);

        Mockito.verify(mockRepository).save(inputTrainee);

        Assertions.assertEquals("Maria", result.getFirstName());
        Assertions.assertEquals("D", result.getLastName());
    }
    @Test
    @DisplayName("POST:(Sad)-> Check creating trainee returns null - sad path")
    void checkFailedTraineeCreationReturnsNullTest() {

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.createTrainee(null)
        );
        Assertions.assertEquals("Trainee cannot be null", exception.getMessage());
        Mockito.verifyNoInteractions(mockRepository);
        Mockito.verifyNoInteractions(mockMapper);
    }
    @Test
    @DisplayName("PATCH:(Sad)->Check if updating id to invalid number throws exception - sad path")
    void checkUpdatingIDtoInvalidNumberThrowsExceptionTest() {

        Trainee trainee = new Trainee();
        trainee.setId(100);

        Mockito.when(mockRepository.existsById(100)).thenReturn(false);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.updateTrainee(trainee)
        );

        Assertions.assertEquals("Trainee cannot be null", exception.getMessage());

        Mockito.verify(mockRepository).existsById(100);
        Mockito.verify(mockRepository, Mockito.never()).save(any());
        Mockito.verifyNoInteractions(mockMapper);
    }

    @Test
    @DisplayName("DELETE:(Sad)->Check if deleteTrainee returns false")
    void checkIfDeleteTraineeWhenIdNotFoundReturnsFalseTest() {

        int testId = 99;
        Mockito.when(mockRepository.existsById(testId)).thenReturn(false);

        boolean result = sut.deleteTrainee(testId);

        Assertions.assertFalse(result, "Method should return false if trainee is missing");

        Mockito.verify(mockRepository).existsById(testId);
        Mockito.verify(mockRepository, Mockito.never()).deleteById(anyInt());
    }
    @Test
    @DisplayName("DELETE:(Happy)->Check if deleteTrainee returns true and call delete when ID exists")
    void checkIfDeleteTraineeIdExistsReturnsTrueTest() {

        int testId = 1;
        Mockito.when(mockRepository.existsById(testId)).thenReturn(true);
        boolean result = sut.deleteTrainee(testId);
        Assertions.assertTrue(result);
        Mockito.verify(mockRepository).existsById(testId);
        Mockito.verify(mockRepository).deleteById(testId);
    }
}

