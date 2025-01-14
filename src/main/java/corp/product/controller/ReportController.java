package corp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import corp.product.util.ExcelReportForTime;
import org.apache.commons.io.IOUtils;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import corp.product.dto.ReportDto;
import corp.product.service.RecordService;
import static corp.product.util.ReportUtil.createReport;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
@RequestMapping("/reports")
public class ReportController {
    final RecordService recordService;

    public ReportController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/report-for-time")
    public String report(@RequestParam(name = "start", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                         @RequestParam(name = "end", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                         Model model) {

        if (start != null && end != null) {
            List<ReportDto> recordsForReport = recordService.getRecords(start, end);
            List<List<ReportDto>> report = createReport(recordsForReport);
            model.addAttribute("list", report);
        }

        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "reports/amount-per-time-report";
    }

    @GetMapping("/report-for-time/download-excel")
    public void downloadExcel(@RequestParam(name = "start", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                              @RequestParam(name = "end", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                              HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename = all_lines_report.xlsx");
        List<ReportDto> records = recordService.getRecords(start, end);
        List<List<ReportDto>> report = createReport(records);
        ByteArrayInputStream stream = ExcelReportForTime.toFile(report, start, end);
        IOUtils.copy(stream, response.getOutputStream());
    }
}
