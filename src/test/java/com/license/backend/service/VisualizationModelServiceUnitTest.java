package com.license.backend.service;

import com.license.backend.domain.dto.visualization_model.VisualizationModelCreateDto;
import com.license.backend.domain.dto.visualization_model.VisualizationModelViewDto;
import com.license.backend.domain.mapper.VisualizationModelMapper;
import com.license.backend.domain.model.VisualizationModel;
import com.license.backend.repository.VisualizationModelRepository;
import com.license.backend.service.impl.VisualizationModelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisualizationModelServiceUnitTest {

    @Mock
    private VisualizationModelRepository repository;

    @Mock
    private VisualizationModelMapper mapper;

    @InjectMocks
    private VisualizationModelServiceImpl service;

    @Mock
    private MultipartFile multipartFile;

    @Test
    public void whenVisualizationModelsFetched_thenFlowAsExpected() {
        VisualizationModel visualizationModel = new VisualizationModel();
        VisualizationModelViewDto viewDto = new VisualizationModelViewDto();

        when(repository.findById(1)).thenReturn(Optional.of(visualizationModel));
        when(mapper.toViewDto(visualizationModel)).thenReturn(viewDto);

        VisualizationModelViewDto result = service.get(1);

        assertEquals(viewDto, result);
        verify(repository).findById(1);
        verify(mapper).toViewDto(visualizationModel);
    }

    @Test
    public void whenVisualizationModelCreated_thenFlowAsExpected() throws IOException {
        VisualizationModelCreateDto createDto = new VisualizationModelCreateDto();
        VisualizationModel model = new VisualizationModel();
        VisualizationModel savedModel = new VisualizationModel();
        VisualizationModelViewDto viewDto = new VisualizationModelViewDto();

        byte[] fileBytes = "test-image-bytes".getBytes();
        when(mapper.toEntity(createDto)).thenReturn(model);
        when(multipartFile.getBytes()).thenReturn(fileBytes);
        when(repository.save(model)).thenReturn(savedModel);
        when(mapper.toViewDto(savedModel)).thenReturn(viewDto);

        VisualizationModelViewDto result = service.create(createDto, multipartFile);

        assertEquals(viewDto, result);
        verify(mapper).toEntity(createDto);
        verify(multipartFile).getBytes();
        verify(repository).save(model);
        verify(mapper).toViewDto(savedModel);
        assertArrayEquals(fileBytes, model.getCardPhoto());
    }

    @Test
    public void whenVisualizationModelUpdated_thenFlowAsExpected() {
        VisualizationModel model = new VisualizationModel();
        model.setName("Old Name");

        Map<String, Object> fields = Map.of("name", "Updated Name");
        VisualizationModelViewDto viewDto = new VisualizationModelViewDto();

        when(repository.findById(1)).thenReturn(Optional.of(model));
        when(repository.save(model)).thenReturn(model);
        when(mapper.toViewDto(model)).thenReturn(viewDto);

        VisualizationModelViewDto result = service.update(1, fields);

        assertEquals(viewDto, result);
        verify(repository).findById(1);
        verify(repository).save(model);
        verify(mapper).toViewDto(model);
        assertEquals("Updated Name", model.getName());
    }

    @Test
    public void whenVisualizationModelDeleted_thenFlowAsExpected() {
        service.delete(1);

        verify(repository).deleteById(1);
    }

}
