package corp.product.controller;

import org.springframework.stereotype.Controller;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import corp.product.service.LineService;
import corp.product.service.RecordService;
import corp.product.data.User;
import corp.product.data.Role;
import corp.product.dto.LineDto;
import corp.product.dto.RecordDto;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/lines/line/")
public class RecordController {
    private final RecordService recordService;

    private final LineService lineService;

    @GetMapping("{lineId}")
    public String list(@PathVariable("lineId") long id,
                       @RequestParam(name = "start", required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                       @RequestParam(name = "end", required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                       @RequestParam(name = "nameOfOrganization", required = false) String nameOfOrganization,
                       @RequestParam(name = "nameOfProduct", required = false) String nameOfProduct,
                       @RequestParam(name = "variant", required = false) String variant,
                       @RequestParam(name = "side", required = false) String side,
                       @RequestParam(name = "surname", required = false) String surname,
                       @PageableDefault(size = 15) Pageable pageable,
                       Model model) {

        LineDto line = lineService.getOne(id);
        Page<RecordDto> page = null;

        if ((start == null || end == null) &&
                (nameOfOrganization == null || nameOfOrganization.isEmpty()) &&
                (nameOfProduct == null || nameOfProduct.isEmpty()) &&
                (variant == null || variant.isEmpty()) &&
                (side == null || side.isEmpty()) &&
                (surname == null || surname.isEmpty())) {

            page = recordService.list(id, pageable);

        } else {
            if (start == null && end == null) {
                start = LocalDate.of(0000, 01, 01);
                end = LocalDate.of(999999, 01, 01);
            }
            page = recordService.filter(id, start, end, nameOfOrganization, nameOfProduct, variant,
                    side, surname, pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("line", line);
        return "records/records";
    }

    @GetMapping("{lineId}/record-create")
    public String showCreateForm(Model model, @PathVariable("lineId") String lineId) {
        model.addAttribute("record", new RecordDto());
        return "records/record-create";
    }

    @PostMapping("{lineId}/record-create")
    public String create(@AuthenticationPrincipal User user,
                         @PathVariable("lineId") long id,
                         @Valid @ModelAttribute("record") RecordDto record,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "records/record-create";
        }
        recordService.create(record, user, id);

        return "redirect:/lines/line/" + id;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
    @GetMapping("{lineId}/record-update/{id}")
    public String updateRecordForm(@PathVariable("lineId") String lineId, @PathVariable("id") Long id, Model model) {
        RecordDto record = recordService.getOne(id);
        model.addAttribute("record", record);
        model.addAttribute("lineid", lineId);

        return "records/record-update";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
    @PostMapping("{lineId}/record-update/{id}")
    public String updateRecord(@PathVariable("lineId") String lineId,
                               @PathVariable("id") Long id,
                               @Valid @ModelAttribute("record") RecordDto record,
                               BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("lineId", lineId);
            model.addAttribute("id", id);
            return "records/record-update";
        }

        record.setId(id);
        recordService.update(record);

        return "redirect:/lines/line/" + lineId;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPERIOR')")
    @GetMapping("{lineId}/record-delete/{id}")
    public String delete(@PathVariable("id") long id) {
        recordService.delete(id);
        return "redirect:/lines/line/{lineId}";
    }

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }
}
