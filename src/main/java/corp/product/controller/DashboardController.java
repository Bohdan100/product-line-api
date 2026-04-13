package corp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import corp.product.service.impl.RecordServiceImpl;

@Controller
@RequestMapping("/admin/dashboard")
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class DashboardController {
    private final RecordServiceImpl recordService;

    @GetMapping
    public String showDashboard(Model model) {
        model.addAttribute("stats", recordService.getDashboardStats());
        return "admin/dashboard";
    }
}
