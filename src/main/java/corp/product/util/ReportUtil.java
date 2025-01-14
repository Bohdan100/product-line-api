package corp.product.util;

import org.springframework.stereotype.Component;
import corp.product.dto.ReportDto;

import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ReportUtil {
    public static List<List<ReportDto>> createReport(List<ReportDto> recordsForReport) {
        List<List<ReportDto>> result = new ArrayList<>();
        List<List<ReportDto>> list = new ArrayList<>(recordsForReport.stream()
                .collect(Collectors.groupingBy(record -> Collections.singletonList(record.getLineId()))).values());
        for (List<ReportDto> item : list) {
            List<List<ReportDto>> sort = new ArrayList<>(item.stream().collect(Collectors.groupingBy(record -> Arrays.asList(
                            record.getNamePr().trim().toLowerCase().replaceAll(" ", ""),
                            record.getVar().trim().toLowerCase().replaceAll(" ", ""),
                            record.getSide().trim().toLowerCase().replaceAll(" ", ""))))
                    .values());
            List<ReportDto> sum = sum(sort);
            result.add(sum);
        }
        return result;
    }

    private static List<ReportDto> sum(List<List<ReportDto>> list) {

        List<ReportDto> sum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<ReportDto> records = list.get(i);
            for (int j = 1; j < records.size(); j++) {
                records.getFirst().setQuantity(records.getFirst().getQuantity() + records.get(j).getQuantity());
            }
            sum.add(records.getFirst());
        }
        return sum;
    }
}
