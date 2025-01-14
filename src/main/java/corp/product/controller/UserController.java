package corp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import corp.product.service.UserService;
import corp.product.data.Role;
import corp.product.dto.UserDto;
import java.util.List;

@Controller
@RequestMapping("users")
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String list(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public String showCreateForm(UserDto user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "users/user-create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String create(
            @RequestParam(value = "roles", required = false) List<Role> roles,
            @Valid @ModelAttribute("user") UserDto user,
            BindingResult bindingResult,
            Model model) {
        if (roles == null || roles.isEmpty()) {
            roles = List.of();
            bindingResult.rejectValue("roles", "error.user", "At least one role must be selected.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", Role.values());
            return "users/user-create";
        }

        try {
            user.setRoles(roles);
            userService.create(user);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "error.user", e.getMessage());
            model.addAttribute("allRoles", Role.values());
            return "users/user-create";
        }

        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "users/user-update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String updateUser(
            @RequestParam(value = "roles", required = false) List<Role> roles,
            @Valid @ModelAttribute("user") UserDto user,
            BindingResult bindingResult,
            Model model) {
        if (roles == null || roles.isEmpty()) {
            roles = List.of();
            bindingResult.rejectValue("roles", "error.user", "At least one role must be selected.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", Role.values());
            return "users/user-update";
        }

        try {
            user.setRoles(roles);
            userService.update(user);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "error.user", e.getMessage());
            model.addAttribute("allRoles", Role.values());
            return "users/user-update";
        }

        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }
}
