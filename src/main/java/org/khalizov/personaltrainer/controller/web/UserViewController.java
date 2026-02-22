package org.khalizov.personaltrainer.controller.web;

import jakarta.validation.Valid;
import org.khalizov.personaltrainer.config.CustomUserDetails;
import org.khalizov.personaltrainer.dto.UserDTO;
import org.khalizov.personaltrainer.dto.UserUpdateDTO;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.UserType;
import org.khalizov.personaltrainer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private static final Logger log = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/edit")
    public String showEditForm(@PathVariable Integer userId,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model) {
        log.debug("Entering showUserProfile: userId={}, principal={}", userId,
                userDetails != null ? userDetails.getUsername() : "anonymous");

        if(userDetails == null || userDetails.isTrainer() ||
                !userDetails.getEffectiveUser().getUserId().equals(userId)) {
            log.warn("Unauthorized or mismatched principal for edit: userId={}, principal={}", userId,
                    userDetails != null ? userDetails.getUsername() : "anonymous");
            return "redirect:/home";
        }

        UserDTO userDTO = userService.getUserById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", userId);
                    return new RuntimeException("User not found");
                });

        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setUserId(userDTO.getUserid());
        dto.setNickname(userDTO.getNickname());
        dto.setEmail(userDTO.getEmail());
        dto.setFirstName(userDTO.getFirstName());
        dto.setLastName(userDTO.getLastName());
        dto.setPhone(userDTO.getPhone());
        dto.setStatus(userDTO.getStatus());
        dto.setUserType(userDTO.getUserType());

        model.addAttribute("user", dto);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("userTypes", UserType.values());

        log.debug("Exiting showUserProfile: userId={}", userId);
        return "user-edit";
    }

    @PostMapping("/{userId}/edit")
    public String updateUser(@PathVariable Integer userId,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             @Valid @ModelAttribute("user") UserUpdateDTO userUpdateDTO,
                             BindingResult result,
                             Model model) {
        log.debug("Entering updateUser: userId={}, principal={}", userId,
                userDetails != null ? userDetails.getUsername() : "anonymous");

        if(userDetails == null || userDetails.isTrainer() ||
                !userDetails.getEffectiveUser().getUserId().equals(userId)) {
            log.warn("Unauthorized update attempt: userId={}, principal={}", userId,
                    userDetails != null ? userDetails.getUsername() : "anonymous");
            return "redirect:/home";
        }

        if (result.hasErrors()) {
            log.info("Validation errors while updating user {}: {}", userId, result.getAllErrors());
            model.addAttribute("statuses", Status.values());
            model.addAttribute("userTypes", UserType.values());
            return "user-edit";
        }

        // exclude passwords in logs
        log.debug("Calling userService.updateUser - userId={}, payload={}", userId, maskSensitive(userUpdateDTO));
        userService.updateUser(userId, userUpdateDTO);
        log.info("userService.updateUser completed - userId={}", userId);

        UserDTO updated = userService.getUserById(userId)
                .orElseThrow(()-> new RuntimeException("User not found after update"));

        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setUserId(updated.getUserid());
        dto.setNickname(updated.getNickname());
        dto.setEmail(updated.getEmail());
        dto.setFirstName(updated.getFirstName());
        dto.setLastName(updated.getLastName());
        dto.setPhone(updated.getPhone());
        dto.setStatus(updated.getStatus());
        dto.setUserType(updated.getUserType());

        model.addAttribute("user", dto);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("userTypes", UserType.values());

        log.debug("Exiting updateUser: userId={}", userId);
        return "user-edit-success";
    }

    private UserUpdateDTO maskSensitive(UserUpdateDTO dto) {
        if (dto == null) return null;
        UserUpdateDTO copy = new UserUpdateDTO();
        copy.setUserId(dto.getUserId());
        copy.setNickname(dto.getNickname());
        copy.setFirstName(dto.getFirstName());
        copy.setLastName(dto.getLastName());
        copy.setEmail(dto.getEmail());
        copy.setPhone(dto.getPhone());
        copy.setStatus(dto.getStatus());
        copy.setUserType(dto.getUserType());

        return copy;
    }
}