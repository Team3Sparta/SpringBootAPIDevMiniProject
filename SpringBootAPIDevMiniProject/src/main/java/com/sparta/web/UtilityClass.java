package com.sparta.web;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class UtilityClass {

    public static boolean isTrainer(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        boolean isTrainer = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_TRAINER"));

        return isTrainer;
    }

    public static void addIsTrainerToModel(Model model) {

        boolean isTrainer = isTrainer();
        model.addAttribute("showCourses", isTrainer);

    }
}
