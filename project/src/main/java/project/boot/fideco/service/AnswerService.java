//package project.boot.fideco.service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import lombok.RequiredArgsConstructor;
//import project.boot.fideco.entity.Answer;
//import project.boot.fideco.entity.Question;
//import project.boot.fideco.entity.Users;
//import project.boot.fideco.repository.AnswerRepository;
//
//@RequiredArgsConstructor
//@Service
//public class AnswerService {
//
//	private final AnswerRepository answerRepository;
//
//	public void create(Question question, String content, Users author) {
//		Answer answer = new Answer();
//		answer.setContent(content);
//		answer.setCreateDate(LocalDateTime.now());
//		answer.setQuestion(question);
//		answer.setAuthor(author);
//		this.answerRepository.save(answer);
//	}
//
//	public Answer getAnswer(Integer id) {
//		Optional<Answer> answer = this.answerRepository.findById(id);
//		if (answer.isPresent()) {
//			return answer.get();
//		} else {
//			throw new DataNotFoundException("answer not found");
//		}
//	}
//
//	public void modify(Answer answer, String content) {
//		answer.setContent(content);
//		answer.setModifyDate(LocalDateTime.now());
//		this.answerRepository.save(answer);
//	}
//
//	public void delete(Answer answer) {
//		this.answerRepository.delete(answer);
//	}
//
//	/* 페이징 */
//	public Page<Answer> getList(Question question, int page) {
//		List<Sort.Order> sorts = new ArrayList<>();
//		sorts.add(Sort.Order.desc("createDate"));
//
//		Pageable pageable = PageRequest.of(page, 5);
//		return this.answerRepository.findAllByQuestion(question, pageable);
//	}
//}