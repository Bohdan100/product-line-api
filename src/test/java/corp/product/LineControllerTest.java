package corp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.ui.Model;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import corp.product.controller.LineController;
import corp.product.service.impl.LineServiceImpl;
import corp.product.repository.LineRepository;
import corp.product.data.Line;
import corp.product.dto.LineDto;
import corp.product.converter.LineConverter;

@ExtendWith(MockitoExtension.class)
class LineControllerTest {

    @Mock
    private LineServiceImpl lineService;

    @Mock
    private LineRepository lineRepository;

    @Mock
    private LineConverter converter;

    @InjectMocks
    private LineController lineController;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowLinesList() {
        List<Line> lines = List.of(new Line(1L, "Line1", null));
        List<LineDto> lineDtos = List.of(new LineDto(1L, "Line1", null));

        when(lineService.list()).thenReturn(lineDtos);

        Model model = mock(Model.class);
        String viewName = lineController.list(model);

        assertEquals("lines/lines", viewName);
        verify(lineService).list();
        verify(model).addAttribute("lines", lineDtos);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowLinesListWhenNoLines() {
        when(lineService.list()).thenReturn(Collections.emptyList());

        Model model = mock(Model.class);
        String viewName = lineController.list(model);

        assertEquals("lines/lines", viewName);
        verify(lineService).list();
        verify(model).addAttribute("lines", Collections.emptyList());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetOneLine() {
        Line line = new Line(1L, "Line1", null);
        LineDto lineDto = new LineDto(1L, "Line1", null);

        when(lineService.getOne(1L)).thenReturn(lineDto);

        LineDto result = lineService.getOne(1L);

        assertNotNull(result);
        assertEquals("Line1", result.getName());
        verify(lineService).getOne(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateLineWhenNameIsEmpty() {
        LineDto emptyNameLineDto = new LineDto(null, "", null);

        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = lineController.create(emptyNameLineDto, bindingResult);

        assertEquals("lines/line-create", result);
        verify(bindingResult).hasErrors();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateLineWhenIdNotFound() {
        when(lineService.getOne(999L)).thenThrow(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> lineController.showUpdateForm(999L, mock(Model.class)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteLine() {
        doNothing().when(lineService).delete(1L);

        String result = lineController.delete(1L);

        assertEquals("redirect:/lines", result);
        verify(lineService).delete(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteLineWhenNonExistingId() {
        doNothing().when(lineService).delete(99L);

        String result = lineController.delete(99L);

        assertEquals("redirect:/lines", result);
        verify(lineService).delete(99L);
    }
}
