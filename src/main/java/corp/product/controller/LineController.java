package corp.product.controller;

import org.springframework.stereotype.Controller;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

import corp.product.service.LineService;
import corp.product.data.Line;
import corp.product.dto.LineDto;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/lines")
public class LineController {
    private final LineService lineService;

    @GetMapping
    public String list(Model model) {
        List<LineDto> lines = lineService.list();
        model.addAttribute("lines", lines);
        return "lines/lines";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public String showCreateForm(Line line, Model model) {
        model.addAttribute("line", line);
        return "lines/line-create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("line") LineDto line,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lines/line-create";
        }
        lineService.createOrUpdate(line);
        return "redirect:/lines";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        LineDto line = lineService.getOne(id);
        model.addAttribute("line", line);
        return "lines/line-update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("line") LineDto line,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lines/line-update";
        }
        lineService.createOrUpdate(line);
        return "redirect:/lines";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        lineService.delete(id);
        return "redirect:/lines";
    }
}
