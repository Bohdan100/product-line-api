package corp.product.service;

import corp.product.service.impl.LineServiceImpl;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.product.repository.LineRepository;
import corp.product.repository.RecordRepository;
import corp.product.repository.UserRepository;
import corp.product.converter.RecordConverter;
import corp.product.converter.ReportConverter;
import corp.product.data.Line;
import corp.product.data.RecordEntity;
import corp.product.data.User;
import corp.product.dto.RecordDto;
import corp.product.dto.ReportDto;
import corp.product.dto.LineDto;
import corp.product.dto.DashboardDto;
import corp.product.exception.types.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final LineServiceImpl lineService;
    private final LineRepository lineRepository;
    private final UserRepository userRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + recordDto.getId()));

        RecordEntity updated = recordConverter.convertFromDto(recordDto);

        updated.setAuthor(existing.getAuthor());
        updated.setLine(existing.getLine());

        recordRepository.save(updated);
    }

    public RecordDto getOne(long id) {
        return recordRepository.findById(id)
                .map(recordConverter::convertFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));
    }

    public DashboardDto getDashboardStats() {
        long totalRecords = recordRepository.countAllRecords();
        long totalUsers = userRepository.countAllUsers();
        List<LineDto> lines = lineService.list();

        Long sumQ = recordRepository.sumAllQuantity();
        long totalQuantity = (sumQ != null) ? sumQ : 0;

        List<Object[]> topData = recordRepository.findTopAuthor();
        String topPerformer = (!topData.isEmpty())
                ? topData.get(0)[0] + " (" + topData.get(0)[1] + " recs)"
                : "No records yet";

        Map<String, Long> recordsPerLine = lines.stream()
                .collect(Collectors.toMap(
                        LineDto::getName,
                        line -> recordRepository.countRecordsByLineId(line.getId())
                ));

        return new DashboardDto(totalRecords, lines.size(), totalUsers, totalQuantity, topPerformer, recordsPerLine);
    }

    public void create(RecordDto recordDto, User author, long id) {
        RecordEntity record = recordConverter.convertFromDto(recordDto);

        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Line not found with id: " + id));

        record.setAuthor(author);
        record.setLine(line);
        recordRepository.save(record);
    }

    public void delete(long id) {
        if (!recordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Record with id " + id + " does not exist");
        }
        recordRepository.deleteById(id);
    }

    public List<ReportDto> getRecords(LocalDate start, LocalDate end) {
        List<RecordEntity> records = recordRepository.findAllByDateBetween(start, end);
        return reportConverter.createFromEntities(records);
    }
}
