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
        Page<RecordEntity> records = recordRepository.findAllByLineId(id, pageable);
        return recordConverter.createFromEntities(records, pageable);
    }

    public Page<RecordDto> filter(long id, LocalDate start, LocalDate end, String org,
                                  String prod, String variant, String side, String surname,
                                  Pageable pageable) {
        Page<RecordEntity> records = recordRepository.filterWithParams(
                id, start, end, org, prod, variant, side, surname, pageable);
        return recordConverter.createFromEntities(records, pageable);
    }

    public void update(RecordDto recordDto) {
        RecordEntity existing = recordRepository.findById(recordDto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        RecordEntity updated = recordConverter.convertFromDto(recordDto);

        updated.setAuthor(existing.getAuthor());
        updated.setLine(existing.getLine());

        recordRepository.save(updated);
    }

    public RecordDto getOne(long id) {
        return recordRepository.findById(id)
                .map(recordConverter::convertFromEntity)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public void create(RecordDto recordDto, User author, long id) {
        RecordEntity record = recordConverter.convertFromDto(recordDto);

        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Line not found with id: " + id));

        record.setAuthor(author);
        record.setLine(line);
        recordRepository.save(record);
    }

    public void delete(long id) {
        recordRepository.deleteById(id);
    }

    public List<ReportDto> getRecords(LocalDate start, LocalDate end) {
        List<RecordEntity> records = recordRepository.findAllByDateBetween(start, end);
        return reportConverter.createFromEntities(records);
    }
}
