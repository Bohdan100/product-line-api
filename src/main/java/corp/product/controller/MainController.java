package corp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.AllArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import corp.product.service.LineService;
import corp.product.data.User;
import corp.product.data.Role;
import corp.product.dto.LineDto;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class MainController {
    private final LineService lineService;

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }

    @ModelAttribute("superior")
    public Role getSuperior() {
        return Role.SUPERIOR;
    }

    @GetMapping
    public String list(Model model, @AuthenticationPrincipal User user) {
        List<LineDto> lines = lineService.list();
        model.addAttribute("lines", lines);
        return "main";
    }
}
