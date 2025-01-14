package corp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

import corp.product.service.RecordService;
import corp.product.repository.LineRepository;
import corp.product.repository.RecordRepository;
import corp.product.converter.RecordConverter;
import corp.product.data.RecordEntity;
import corp.product.dto.RecordDto;
import corp.product.data.Line;
import corp.product.data.User;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private LineRepository lineRepository;

    @Mock
    private RecordConverter recordConverter;

    @InjectMocks
    private RecordService recordService;

    @Test
    void testRecordList() {
        long lineId = 1L;
        Pageable pageable = mock(Pageable.class);
        Page<RecordEntity> recordPage = mock(Page.class);
        Page<RecordDto> recordDtoPage = mock(Page.class);

        when(recordRepository.getRecordsPageable(lineId, pageable)).thenReturn(recordPage);
        when(recordConverter.createFromEntities(recordPage, pageable)).thenReturn(recordDtoPage);

        Page<RecordDto> result = recordService.list(lineId, pageable);

        assertEquals(recordDtoPage, result);
        verify(recordRepository).getRecordsPageable(lineId, pageable);
    }

    @Test
    void testRecordFilter() {
        long lineId = 1L;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        String nameOfOrganization = "Org";
        String nameOfProduct = "Product";
        String variant = "Variant";
        String side = "Side";
        String surname = "Surname";
        Pageable pageable = mock(Pageable.class);

        Page<RecordEntity> filteredRecords = mock(Page.class);
        Page<RecordDto> filteredRecordsDto = mock(Page.class);

        when(recordRepository.filter(lineId, start, end, nameOfOrganization, nameOfProduct, variant,
                side, surname, pageable)).thenReturn(filteredRecords);
        when(recordConverter.createFromEntities(filteredRecords, pageable)).thenReturn(filteredRecordsDto);

        Page<RecordDto> result = recordService.filter(lineId, start, end, nameOfOrganization,
                nameOfProduct, variant, side, surname, pageable);

        // Assert
        assertEquals(filteredRecordsDto, result);
        verify(recordRepository).filter(lineId, start, end, nameOfOrganization, nameOfProduct, variant,
                side, surname, pageable);
    }

    @Test
    void testRecordCreate() {
        RecordDto recordDto = mock(RecordDto.class);
        User user = mock(User.class);
        long lineId = 1L;
        Line line = mock(Line.class);
        RecordEntity recordEntity = mock(RecordEntity.class);

        when(recordConverter.convertFromDto(recordDto)).thenReturn(recordEntity);
        when(lineRepository.getOne(lineId)).thenReturn(line);

        recordService.create(recordDto, user, lineId);

        verify(recordRepository).save(recordEntity);
        verify(recordEntity).setAuthor(user);
        verify(recordEntity).setLine(line);
    }

    @Test
    void testRecordDelete() {
        long recordId = 1L;
        recordService.delete(recordId);

        verify(recordRepository).deleteById(recordId);
    }

    @Test
    void testRecordCreateWithNullData() {
        RecordDto recordDto = null;
        User user = mock(User.class);
        long lineId = 1L;
        Line line = mock(Line.class);

        assertThrows(NullPointerException.class, () -> recordService.create(recordDto, user, lineId));
    }

    @Test
    void testRecordDeleteWithNonExistingId() {
        long recordId = 999L;
        doThrow(new IllegalArgumentException("Record not found")).when(recordRepository).deleteById(recordId);

        assertThrows(IllegalArgumentException.class, () -> recordService.delete(recordId));
    }

    @Test
    void testEmptyRecordList() {
        long lineId = 1L;
        Pageable pageable = mock(Pageable.class);

        Page<RecordEntity> emptyPage = Page.empty();
        Page<RecordDto> emptyDtoPage = Page.empty();

        when(recordRepository.getRecordsPageable(lineId, pageable)).thenReturn(emptyPage);
        when(recordConverter.createFromEntities(emptyPage, pageable)).thenReturn(emptyDtoPage);

        Page<RecordDto> result = recordService.list(lineId, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFilterRecordsWithEmptyResults() {
        long lineId = 1L;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        String nameOfOrganization = "Non-existent Org";
        String nameOfProduct = "Non-existent Product";
        String variant = "Non-existent Variant";
        String side = "Non-existent Side";
        String surname = "Non-existent Surname";
        Pageable pageable = mock(Pageable.class);

        Page<RecordEntity> emptyPage = Page.empty();
        Page<RecordDto> emptyDtoPage = Page.empty();

        when(recordRepository.filter(lineId, start, end, nameOfOrganization, nameOfProduct, variant,
                side, surname, pageable)).thenReturn(emptyPage);
        when(recordConverter.createFromEntities(emptyPage, pageable)).thenReturn(emptyDtoPage);

        Page<RecordDto> result = recordService.filter(lineId, start, end, nameOfOrganization,
                nameOfProduct, variant, side, surname, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
