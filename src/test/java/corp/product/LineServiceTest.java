package corp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import corp.product.data.Line;
import corp.product.dto.LineDto;
import corp.product.repository.LineRepository;
import corp.product.service.impl.LineServiceImpl;
import corp.product.converter.LineConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LineServiceTest {

    @Mock
    private LineRepository lineRepository;

    @Mock
    private LineConverter converter;

    @InjectMocks
    private LineServiceImpl lineService;

    @Test
    void testGetLineList() {
        List<Line> lines = List.of(new Line(1L, "Line1", null));
        List<LineDto> lineDtos = List.of(new LineDto(1L, "Line1", null));

        when(lineRepository.findAllByOrderByIdAsc()).thenReturn(lines);
        when(converter.createFromEntities(lines)).thenReturn(lineDtos);

        List<LineDto> result = lineService.list();

        assertEquals(1, result.size());
        assertEquals("Line1", result.get(0).getName());
        verify(lineRepository).findAllByOrderByIdAsc();
    }

    @Test
    void testGetLineEmptyList() {
        when(lineRepository.findAllByOrderByIdAsc()).thenReturn(Collections.emptyList());
        when(converter.createFromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<LineDto> result = lineService.list();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetOneLine() {
        Line line = new Line(1L, "Line1", null);
        LineDto lineDto = new LineDto(1L, "Line1", null);

        when(lineRepository.findById(1L)).thenReturn(Optional.of(line));
        when(converter.convertFromEntity(line)).thenReturn(lineDto);

        LineDto result = lineService.getOne(1L);

        assertNotNull(result);
        assertEquals("Line1", result.getName());
        verify(lineRepository).findById(1L);
    }

    @Test
    void testGetOneLineWhenNonExistingId() {
        when(lineRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> lineService.getOne(99L));
        assertTrue(exception.getMessage().contains("Line not found"));

        verify(lineRepository).findById(99L);
    }

    @Test
    void testCreateOrUpdateLine() {
        LineDto lineDto = new LineDto(1L, "Line1", null);
        Line line = new Line(1L, "Line1", null);

        when(converter.convertFromDto(lineDto)).thenReturn(line);
        when(lineRepository.save(line)).thenReturn(line);

        lineService.createOrUpdate(lineDto);

        verify(lineRepository).save(line);
    }

    @Test
    void testDeleteLine() {
        doNothing().when(lineRepository).deleteById(1L);

        long id = 1L;
        lineService.delete(id);

        verify(lineRepository).deleteById(id);
    }

    @Test
    void testDeleteLineWhenInvalidId() {
        long invalidId = -1L;

        assertThrows(IllegalArgumentException.class, () -> lineService.delete(invalidId));

        verifyNoInteractions(lineRepository);
    }
}