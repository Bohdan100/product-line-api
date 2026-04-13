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
import java.util.Optional;

import corp.product.service.impl.RecordServiceImpl;
import corp.product.repository.LineRepository;
import corp.product.repository.RecordRepository;
import corp.product.converter.RecordConverter;
import corp.product.converter.ReportConverter;
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

    @Mock
    private ReportConverter reportConverter;

    @InjectMocks
    private RecordServiceImpl recordService;

    @Test
    void testRecordList() {
        long lineId = 1L;
        Pageable pageable = mock(Pageable.class);
        Page<RecordEntity> recordPage = mock(Page.class);
        Page<RecordDto> recordDtoPage = mock(Page.class);

        when(recordRepository.findAllByLineId(lineId, pageable)).thenReturn(recordPage);
        when(recordConverter.createFromEntities(recordPage, pageable)).thenReturn(recordDtoPage);

        Page<RecordDto> result = recordService.list(lineId, pageable);

        assertEquals(recordDtoPage, result);
        verify(recordRepository).findAllByLineId(lineId, pageable);
    }

    @Test
    void testRecordFilter() {
        long lineId = 1L;
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        String org = "Org";
        String prod = "Product";
        String var = "Variant";
        String side = "Side";
        String surname = "Surname";
        Pageable pageable = mock(Pageable.class);

        Page<RecordEntity> filteredRecords = mock(Page.class);
        Page<RecordDto> filteredRecordsDto = mock(Page.class);

        when(recordRepository.filterWithParams(lineId, start, end, org, prod, var,
                side, surname, pageable)).thenReturn(filteredRecords);
        when(recordConverter.createFromEntities(filteredRecords, pageable)).thenReturn(filteredRecordsDto);

        Page<RecordDto> result = recordService.filter(lineId, start, end, org,
                prod, var, side, surname, pageable);

        assertEquals(filteredRecordsDto, result);
        verify(recordRepository).filterWithParams(lineId, start, end, org, prod, var, side, surname, pageable);
    }

    @Test
    void testRecordCreate() {
        RecordDto recordDto = mock(RecordDto.class);
        User user = mock(User.class);
        long lineId = 1L;
        Line line = mock(Line.class);
        RecordEntity recordEntity = mock(RecordEntity.class);

        when(recordConverter.convertFromDto(recordDto)).thenReturn(recordEntity);

        when(lineRepository.findById(lineId)).thenReturn(Optional.of(line));

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
    void testRecordCreateWithNonExistingLine() {
        RecordDto recordDto = mock(RecordDto.class);
        User user = mock(User.class);
        long lineId = 99L;

        when(lineRepository.findById(lineId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> recordService.create(recordDto, user, lineId));
    }

    @Test
    void testEmptyRecordList() {
        long lineId = 1L;
        Pageable pageable = mock(Pageable.class);

        Page<RecordEntity> emptyPage = Page.empty();
        Page<RecordDto> emptyDtoPage = Page.empty();

        when(recordRepository.findAllByLineId(lineId, pageable)).thenReturn(emptyPage);
        when(recordConverter.createFromEntities(emptyPage, pageable)).thenReturn(emptyDtoPage);

        Page<RecordDto> result = recordService.list(lineId, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}