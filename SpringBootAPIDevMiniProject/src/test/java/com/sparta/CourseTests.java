package com.sparta;

import com.sparta.dtos.CourseDTO;
import com.sparta.dtos.CourseMapper;
import com.sparta.entities.Course;
import com.sparta.repositories.CourseRepository;
import com.sparta.services.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseTests {

    private final CourseRepository mockRepository = Mockito.mock(CourseRepository.class);
    private final CourseMapper mockMapper = Mockito.mock(CourseMapper.class);

    private final CourseService sut = new CourseService(mockRepository,mockMapper);

    static Course entity1, entity2,entity3;
    static CourseDTO entityDto1, entityDto2,entityDto3;

    @BeforeAll
    public static void setUp(){

        entity1 = new Course();
        entity1.setCourseName("Java Programming");
        entity1.setId(1);

        entityDto1 = new CourseDTO();
        entityDto1.setCourseName("Java Programming");
        entityDto1.setId(1);

        entity2 = new Course();
        entity2.setCourseName("Bython Programming");
        entity2.setId(2);

        entityDto2 = new CourseDTO();
        entityDto2.setCourseName("Bython Programming");
        entityDto2.setId(2);

        entity3 = new Course();
        entity3.setCourseName("Java Programming");
        entity3.setId(3);

        entityDto3 = new CourseDTO();
        entityDto3.setCourseName("Advanced Java Programming");
        entityDto3.setId(3);

    }

    @Test
    @DisplayName("Get All Courses Test")
    public void getAllentitiesListTest(){
        // Arrange
        List<Course> entitiesList = new ArrayList<>();

        entitiesList.add(entity1);
        entitiesList.add(entity2);

        Mockito.when(mockMapper.toDTO(entity1)).thenReturn(entityDto1);
        Mockito.when(mockMapper.toDTO(entity2)).thenReturn(entityDto2);
        Mockito.when(mockRepository.findAll()).thenReturn(entitiesList);
        // Act
        List<CourseDTO> result = sut.getAllCourses();
        //Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Java Programming", result.get(0).getCourseName());
        Assertions.assertEquals("Bython Programming", result.get(1).getCourseName());
    }

    @Test
    @DisplayName("Save Course Happy Path")
    public void saveCourseHappyPathTest() {
        Mockito.when(mockRepository.save(entity1)).thenReturn(entity1);
        Mockito.when(mockMapper.toDTO(entity1)).thenReturn(entityDto1);
        Mockito.when(mockMapper.toEntity(entityDto1)).thenReturn(entity1);
        CourseDTO savedEntity = sut.saveCourse(entityDto1);
        Assertions.assertNotNull(savedEntity, "The saved course should not be null");
        Assertions.assertEquals("Java Programming", savedEntity.getCourseName(), "Course name should match");
    }

    @Test
    @DisplayName("Sad Path Save 1")
    void sadPathSaveCustomer1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.saveCourse(null));

    }

    @Test
    @DisplayName("Sad Path Save 2")
    void sadPathSaveCustomer2() {


        CourseDTO entityDto = new CourseDTO();
        entityDto.setCourseName("Bython");
        entityDto.setId(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.saveCourse(entityDto));

    }

    @Test
    @DisplayName("Check findById is called once")
    void checkFindByIdIsCalledOnceOnRepository() {
        Mockito.when(mockRepository.existsById(Mockito.anyInt())).thenReturn(true);
        sut.getEntityByID(Mockito.anyInt());
        Mockito.verify(mockRepository).findById(Mockito.anyInt());
        Mockito.verify(mockRepository, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    @DisplayName("Happy Path getCustomerByID")
    void happyPathGetCustomerById() {

        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity1));
        Mockito.when(mockRepository.existsById(Mockito.anyInt())).thenReturn(true);

        Mockito.when(mockMapper.toDTO(entity1)).thenReturn(entityDto1);

        CourseDTO result = sut.getEntityByID(Mockito.anyInt());
        //Assert
        Assertions.assertEquals("Java Programming", result.getCourseName());

    }

    @Test
    @DisplayName("Sad Path getCustomerByID")
    void sadPathGetEntityId1() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> sut.getEntityByID(Mockito.anyInt()));
        Mockito.verify(mockRepository, Mockito.times(1)).existsById(Mockito.anyInt());
        Mockito.verify(mockRepository, Mockito.never()).findById(Mockito.anyInt());

    }

    /*
    @Test
    @DisplayName("Happy path exclude by name")
    void happyPathExcludeByName() {

        List<Course> entitiesList = new ArrayList<>();

        entitiesList.add(entity1);
        entitiesList.add(entity2);
        entitiesList.add(entity3);

        Mockito.when(mockMapper.toDTO(entity1)).thenReturn(entityDto1);
        Mockito.when(mockMapper.toDTO(entity2)).thenReturn(entityDto2);
        Mockito.when(mockMapper.toDTO(entity3)).thenReturn(entityDto3);
        Mockito.when(mockRepository.findAll()).thenReturn(entitiesList);
        // Act
        List<CourseDTO> result = sut.getAllCourses();
        //Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Java Programming", result.get(0).getCourseName());
        Assertions.assertEquals("Bython Programming", result.get(1).getCourseName());

    }
*/
}
