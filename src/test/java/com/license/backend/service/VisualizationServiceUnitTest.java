package com.license.backend.service;

import com.license.backend.domain.dto.visualization.BarchartCreateDto;
import com.license.backend.domain.dto.visualization.BarchartViewDto;
import com.license.backend.domain.dto.visualization.VisualizationCreateDto;
import com.license.backend.domain.dto.visualization.VisualizationViewDto;
import com.license.backend.domain.mapper.VisualizationMapper;
import com.license.backend.domain.model.BarchartVisualization;
import com.license.backend.domain.model.User;
import com.license.backend.domain.model.Visualization;
import com.license.backend.domain.model.VisualizationModel;
import com.license.backend.exception.InaccessibleException;
import com.license.backend.repository.VisualizationModelRepository;
import com.license.backend.repository.VisualizationRepository;
import com.license.backend.service.impl.VisualizationServiceImpl;
import com.license.backend.util.ContextUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisualizationServiceUnitTest {

    @Mock
    private VisualizationRepository visualizationRepository;

    @Mock
    private VisualizationModelRepository visualizationModelRepository;

    @Mock
    private VisualizationMapper visualizationMapper;

    @InjectMocks
    private VisualizationServiceImpl visualizationService;

    private MockedStatic<ContextUtil> contextUtilMock;

    private User testUser;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(UserServiceUnitTest.class);
    }

    @BeforeEach
    void setUp() {
        contextUtilMock = mockStatic(ContextUtil.class);
        testUser = new User();
        testUser.setUserId(1);
    }

    @AfterEach
    void tearDown() {
        contextUtilMock.close();
    }

    @Test
    public void whenVisualizationCreated_thenFlowAsExpected() {
        VisualizationCreateDto createDto = new BarchartCreateDto();
        createDto.setVisualizationModelId(10);

        Visualization visualization = new BarchartVisualization();
        Visualization saved = new BarchartVisualization();
        saved.setVisualizationId(1);
        VisualizationViewDto viewDto = new BarchartViewDto();

        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(testUser);

        when(visualizationMapper.toEntity(createDto)).thenReturn(visualization);
        when(visualizationModelRepository.findById(10)).thenReturn(Optional.of(new VisualizationModel()));
        when(visualizationRepository.save(any())).thenReturn(saved);
        when(visualizationMapper.toViewDto(saved)).thenReturn(viewDto);

        VisualizationViewDto result = visualizationService.create(createDto);

        assertEquals(viewDto, result);
        verify(visualizationRepository).save(visualization);
    }

    @Test
    public void whenVisualizationsFetched_thenFlowAsExpected() {
        List<Visualization> visualizations = List.of(new BarchartVisualization());
        VisualizationViewDto viewDto = new BarchartViewDto();

        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(testUser);

        when(visualizationRepository.findVisualizationsByUser(testUser)).thenReturn(visualizations);
        when(visualizationMapper.toViewDto(any())).thenReturn(viewDto);

        List<VisualizationViewDto> result = visualizationService.get();

        assertEquals(1, result.size());
    }

    @Test
    public void whenVisualizationFetched_thenFlowAsExpected() {
        Visualization visualization = new BarchartVisualization();
        visualization.setUser(testUser);
        visualization.setVisualizationId(1);
        VisualizationViewDto viewDto = new BarchartViewDto();

        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(testUser);

        when(visualizationRepository.findById(1)).thenReturn(Optional.of(visualization));
        when(visualizationMapper.toViewDto(visualization)).thenReturn(viewDto);

        VisualizationViewDto result = visualizationService.get(1);
        assertEquals(viewDto, result);
    }

    @Test
    public void whenVisualizationFetched_exceptionThrown() {
        Visualization visualization = new BarchartVisualization();
        User anotherUser = new User();
        anotherUser.setUserId(2);
        visualization.setUser(anotherUser);

        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(testUser);

        when(visualizationRepository.findById(1)).thenReturn(Optional.of(visualization));

        assertThrows(InaccessibleException.class, () -> visualizationService.get(1));
    }

    @Test
    public void whenVisualizationPublished_thenFlowAsExpected() {
        Visualization visualization = new BarchartVisualization();
        visualization.setUser(testUser);
        visualization.setIsPublished(false);

        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(testUser);

        when(visualizationRepository.findById(1)).thenReturn(Optional.of(visualization));

        visualizationService.updateIsPublished(1);

        assertTrue(visualization.getIsPublished());
        verify(visualizationRepository).save(visualization);
    }

    @Test
    public void whenVisualizationDeleted_thenDeleteIfOwner() {
        Visualization visualization = new BarchartVisualization();
        visualization.setUser(testUser);

        contextUtilMock.when(ContextUtil::getAuthenticatedUser).thenReturn(testUser);

        when(visualizationRepository.findById(1)).thenReturn(Optional.of(visualization));

        visualizationService.delete(1);

        verify(visualizationRepository).delete(visualization);
    }

}
