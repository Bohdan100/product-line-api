package corp.product.service;


import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.product.repository.LineRepository;
import corp.product.repository.RecordRepository;

import corp.product.converter.RecordConverter;
import corp.product.converter.ReportConverter;
import corp.product.dto.RecordDto;
import corp.product.dto.ReportDto;
import corp.product.data.Line;
import corp.product.data.RecordEntity;
import corp.product.data.User;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    private final LineRepository lineRepository;

    private final ReportConverter reportConverter;

    private final RecordConverter recordConverter;

    public Page<RecordDto> list(long id, Pageable pageable) {
        Page<RecordEntity> records = recordRepository.getRecordsPageable(id, pageable);
        return recordConverter.createFromEntities(records, pageable);
    }

    public Page<RecordDto> filter(long id,
                                  LocalDate start,
                                  LocalDate end,
                                  String nameOfOrganization,
                                  String nameOfProduct,
                                  String variant,
                                  String side,
                                  String surname,
                                  Pageable pageable) {
        Page<RecordEntity> records = recordRepository.filter(id,
                start,
                end,
                nameOfOrganization,
                nameOfProduct,
                variant,
                side,
                surname,
                pageable);
        return recordConverter.createFromEntities(records, pageable);
    }

    public void update(RecordDto recordDto) {
        RecordEntity record = recordConverter.convertFromDto(recordDto);
        RecordEntity recordById = recordRepository.getRecordById(recordDto.getId());
        record.setAuthor(recordById.getAuthor());
        record.setLine(recordById.getLine());
        recordRepository.save(record);
    }

    public RecordDto getOne(long id) {
        RecordEntity record = recordRepository.getRecordById(id);
        return recordConverter.convertFromEntity(record);
    }

    public void create(RecordDto recordDto, User author, long id) {
        RecordEntity record = recordConverter.convertFromDto(recordDto);
        Line line = lineRepository.getOne(id);
        record.setAuthor(author);
        record.setLine(line);
        recordRepository.save(record);
    }

    public void delete(long id) {
        recordRepository.deleteById(id);
    }

    public List<ReportDto> getRecords(LocalDate start, LocalDate end) {
        List<RecordEntity> recordsForReport = recordRepository.getRecordsBetweenDate(start, end);
        return reportConverter.createFromEntities(recordsForReport);
    }
}
