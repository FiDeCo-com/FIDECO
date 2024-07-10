//
//package project.boot.fideco.controller;
//
//import lombok.RequiredArgsConstructor;
//import project.boot.fideco.dto.AnswerDTO;
//import project.boot.fideco.entity.Answer;
//import project.boot.fideco.entity.Question;
//import project.boot.fideco.entity.Users;
//import project.boot.fideco.service.AnswerService;
//import project.boot.fideco.service.QuestionService;
//import project.boot.fideco.service.UserService;
//
//import java.security.Principal;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.server.ResponseStatusException;
//
//import jakarta.validation.Valid;
//
//@RequestMapping("/answer")
//@RequiredArgsConstructor
//@Controller
//public class AnswerController {
//
//	private final QuestionService questionService;
//	private final AnswerService answerService;
//	private final UserService userService;
//
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/create/{id}")
//	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerDTO answerForm,
//			BindingResult bindingResult, Principal principal) {
//		Question question = this.questionService.getQuestion(id);
//		Users users = this.userService.getUser(principal.getName());
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("question", question);
//			return "question_detail";
//		}
//		this.answerService.create(question, answerForm.getContent(), users);
//		return String.format("redirect:/question/detail/%s", id);
//	}
//
//	@PreAuthorize("isAuthenticated()")
//	@GetMapping("/modify/{id}")
//	public String answerModify(AnswerDTO answerDTO, @PathVariable("id") Integer id, Principal principal) {
//		Answer answer = this.answerService.getAnswer(id);
//		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//		}
//		answerDTO.setContent(answer.getContent());
//		return "answer_form";
//	}
//
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/modify/{id}")
//	public String answerModify(@Valid AnswerDTO answerDTO, BindingResult bindingResult, @PathVariable("id") Integer id,
//			Principal principal) {
//		if (bindingResult.hasErrors()) {
//			return "answer_form";
//		}
//		Answer answer = this.answerService.getAnswer(id);
//		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//		}
//		this.answerService.modify(answer, answerDTO.getContent());
//		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
//	}
//
//	@PreAuthorize("isAuthenticated()")
//	@GetMapping("/delete/{id}")
//	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
//		Answer answer = this.answerService.getAnswer(id);
//		if (!answer.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//		}
//		this.answerService.delete(answer);
//		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
//	}
//}
