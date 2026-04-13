package corp.product.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
public class DashboardDto {
    private long totalRecords;
    private long totalLines;
    private long totalUsers;
    private long totalQuantity;
    private String topPerformer;
    private Map<String, Long> recordsPerLine;
}
