package project.boot.fideco.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.boot.fideco.dto.request.auth.LogInRequestDTO;
import project.boot.fideco.dto.response.auth.LogInResponseDTO;
import project.boot.fideco.login.service.LoginService;
import project.boot.fideco.member.entity.MemberEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/log-in")
    public String loginForm(Model model) {
        model.addAttribute("memberEntity", new MemberEntity());
        return "login";
    }

    @PostMapping("/log-in")
    public ResponseEntity<? super LogInResponseDTO> logIn(
            @RequestBody @Valid LogInRequestDTO requestBody, HttpServletResponse response) {
        ResponseEntity<? super LogInResponseDTO> responseEntity = loginService.logIn(requestBody, response);
        return responseEntity;
    }

    @GetMapping("/log-out")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index";
    }
}
