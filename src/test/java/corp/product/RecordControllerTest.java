package corp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.product.controller.RecordController;
import corp.product.service.impl.LineServiceImpl;
import corp.product.service.impl.RecordServiceImpl;
import corp.product.data.User;
import corp.product.dto.RecordDto;
import corp.product.dto.LineDto;

@ExtendWith(MockitoExtension.class)
class RecordControllerTest {

    @Mock
    private RecordServiceImpl recordService;

    @Mock
    private LineServiceImpl lineService;

    @Mock
    private Model model;

    @InjectMocks
    private RecordController recordController;

    @Test
    void testShowRecordList() {
        long lineId = 1L;
        Pageable pageable = mock(Pageable.class);
        LineDto lineDto = mock(LineDto.class);
        Page<RecordDto> page = mock(Page.class);

        when(lineService.getOne(lineId)).thenReturn(lineDto);
        when(recordService.list(lineId, pageable)).thenReturn(page);

        String result = recordController.list(lineId, null, null, null, null, null, null, null, pageable, model);

        verify(recordService).list(lineId, pageable);
        verify(model).addAttribute("page", page);
        verify(model).addAttribute("line", lineDto);
        assertEquals("records/records", result);
    }

    @Test
    void testShowCreateForm() {
        String result = recordController.showCreateForm(model, "1");

        verify(model).addAttribute(eq("record"), any(RecordDto.class));
        assertEquals("records/record-create", result);
    }

    @Test
    void testCreateRecordWithErrors() {
        RecordDto recordDto = new RecordDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = recordController.create(mock(User.class), 1L, recordDto, bindingResult);

        assertEquals("records/record-create", result);
    }

    @Test
    void testCreateRecordNoErrors() {
        RecordDto recordDto = new RecordDto();
        BindingResult bindingResult = mock(BindingResult.class);
        User user = mock(User.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = recordController.create(user, 1L, recordDto, bindingResult);

        verify(recordService).create(recordDto, user, 1L);
        assertEquals("redirect:/lines/line/1", result);
    }

    @Test
    void testUpdateRecordForm() {
        RecordDto recordDto = new RecordDto();
        when(recordService.getOne(1L)).thenReturn(recordDto);

        String result = recordController.updateRecordForm("1", 1L, model);

        verify(recordService).getOne(1L);
        verify(model).addAttribute("record", recordDto);
        verify(model).addAttribute("lineid", "1");
        assertEquals("records/record-update", result);
    }

    @Test
    void testUpdateRecordWithErrors() {
        RecordDto recordDto = new RecordDto();
        Long recordId = 33L;
        String lineId = "1";
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = recordController.updateRecord(lineId, recordId, recordDto, bindingResult, model);

        verify(model).addAttribute("lineId", lineId);
        verify(model).addAttribute("id", recordId);
        assertEquals("records/record-update", result);
    }

    @Test
    void testUpdateRecordNoErrors() {
        RecordDto recordDto = new RecordDto();
        Long recordId = 33L;
        String lineId = "1";
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = recordController.updateRecord(lineId, recordId, recordDto, bindingResult, model);

        verify(recordService).update(recordDto);

        assertEquals(recordId, recordDto.getId());
        assertEquals("redirect:/lines/line/" + lineId, result);
    }

    @Test
    void testDeleteRecord() {
        String result = recordController.delete(1L);

        verify(recordService).delete(1L);
        assertEquals("redirect:/lines/line/{lineId}", result);
    }
}

