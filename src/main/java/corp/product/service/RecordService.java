package corp.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import corp.product.dto.RecordDto;
import corp.product.dto.ReportDto;
import corp.product.dto.DashboardDto;
import corp.product.data.User;
import java.time.LocalDate;
import java.util.List;

public interface RecordService {
    Page<RecordDto> list(long id, Pageable pageable);
    Page<RecordDto> filter(long id, LocalDate start, LocalDate end, String org,
                           String prod, String variant, String side, String surname,
                           Pageable pageable);
    void update(RecordDto recordDto);
    RecordDto getOne(long id);
    DashboardDto getDashboardStats();
    void create(RecordDto recordDto, User author, long id);
    void delete(long id);
    List<ReportDto> getRecords(LocalDate start, LocalDate end);
}