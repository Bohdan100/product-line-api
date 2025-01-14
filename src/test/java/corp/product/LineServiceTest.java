package corp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import corp.product.data.Line;
import corp.product.dto.LineDto;
import corp.product.repository.LineRepository;
import corp.product.service.LineService;
import corp.product.converter.LineConverter;

import jakarta.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LineServiceTest {

    @Mock
    private LineRepository lineRepository;

    @Mock
    private LineConverter converter;

    @InjectMocks
    private LineService lineService;

    @Test
    void testGetLineList() {
        List<Line> lines = List.of(new Line(1L, "Line1", null));
        List<LineDto> lineDtos = List.of(new LineDto(1L, "Line1", null));

        when(lineRepository.findAll()).thenReturn(lines);
        when(converter.createFromEntities(lines)).thenReturn(lineDtos);

        List<LineDto> result = lineService.list();

        assertEquals(1, result.size());
        assertEquals("Line1", result.get(0).getName());
        verify(lineRepository).findAll();
        verify(converter).createFromEntities(lines);
    }

    @Test
    void testGetLineEmptyList() {
        when(lineRepository.findAll()).thenReturn(Collections.emptyList());
        when(converter.createFromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<LineDto> result = lineService.list();

        assertTrue(result.isEmpty());
        verify(lineRepository).findAll();
        verify(converter).createFromEntities(Collections.emptyList());
    }

    @Test
    void testGetOneLine() {
        Line line = new Line(1L, "Line1", null);
        LineDto lineDto = new LineDto(1L, "Line1", null);

        when(lineRepository.getOne(1L)).thenReturn(line);
        when(converter.convertFromEntity(line)).thenReturn(lineDto);

        LineDto result = lineService.getOne(1L);

        assertNotNull(result);
        assertEquals("Line1", result.getName());
        verify(lineRepository).getOne(1L);
        verify(converter).convertFromEntity(line);
    }

    @Test
    void testGetOneLineWhenNonExistingId() {
        when(lineRepository.getOne(99L)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> lineService.getOne(99L));
        verify(lineRepository).getOne(99L);
    }

    @Test
    void testCreateOrUpdateLine() {
        LineDto lineDto = new LineDto(1L, "Line1", null);
        Line line = new Line(1L, "Line1", null);

        when(converter.convertFromDto(lineDto)).thenReturn(line);
        when(lineRepository.save(line)).thenReturn(line);

        lineService.createOrUpdate(lineDto);

        verify(lineRepository).save(line);
        verify(converter).convertFromDto(lineDto);
    }

    @Test
    void testCreateOrUpdateLineWhenInvalidEntity() {
        LineDto invalidDto = new LineDto(null, null, null);
        when(converter.convertFromDto(invalidDto)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> lineService.createOrUpdate(invalidDto));
        verify(converter).convertFromDto(invalidDto);
    }

    @Test
    void testDeleteLine() {
        doNothing().when(lineRepository).deleteById(1L);

        lineService.delete(1L);

        verify(lineRepository).deleteById(1L);
    }

    @Test
    void testDeleteLineWhenInvalidId() {
        long invalidId = -1L;

        assertThrows(IllegalArgumentException.class, () -> lineService.delete(invalidId));
    }

    @Test
    void testDeleteLineWhenNonExistingId() {
        doNothing().when(lineRepository).deleteById(99L);

        lineService.delete(99L);

        verify(lineRepository).deleteById(99L);
    }
}
