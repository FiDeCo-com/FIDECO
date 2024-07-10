//package project.boot.fideco.service;
//
//import java.io.File;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Join;
//import jakarta.persistence.criteria.JoinType;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import lombok.RequiredArgsConstructor;
//import project.boot.fideco.entity.Answer;
//import project.boot.fideco.entity.Question;
//import project.boot.fideco.entity.Users;
//import project.boot.fideco.repository.QuestionRepository;
//
//@RequiredArgsConstructor
//@Service
//public class QuestionService {
//
//	private final QuestionRepository questionRepository;
//
//	// 파일저장할위치
//	@Value("${ImgLocation}")
//	private String imgLocation;
//
//	private Specification<Question> search(String kw) {
//		return new Specification<>() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				query.distinct(true); // 중복을 제거
//				Join<Question, Users> u1 = q.join("author", JoinType.LEFT);
//				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
//				Join<Answer, Users> u2 = a.join("author", JoinType.LEFT);
//				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
//						cb.like(q.get("content"), "%" + kw + "%"), // 내용
//						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
//						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
//						cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
//			}
//		};
//	}
//
//	public List<Question> getList() {
//		return this.questionRepository.findAll();
//	}
//
//	public Question getQuestion(Integer id) {
//		Optional<Question> question = this.questionRepository.findById(id);
//		if (question.isPresent()) {
//
//			// 조회수
//			Question question1 = question.get();
//			question1.setCountview(question1.getCountview() + 1);
//			this.questionRepository.save(question1);
//			return question1;
//			// 조회수끝
//			// return question.get();
//		} else {
//			throw new DataNotFoundException("question not found");
//		}
//	}
//
//	public void create(String subject, String content, Users users, MultipartFile file) throws Exception {
//
//		String projectPath = imgLocation; // 파일저장위치 = projectPath
//		
//		UUID uuid = UUID.randomUUID(); // 식별자.랜덤으로 이름만들어줌
//		String fileName = uuid + "_" + file.getOriginalFilename(); // 저장될파일이름지정=랜덤식별자_원래파일이름
//		File saveFile = new File(projectPath, fileName); // 빈껍데기생성 이름은 fileName, projectPath라는 경로에담김
//		file.transferTo(saveFile);
//
//		Question q = new Question();
//		q.setSubject(subject);
//		q.setContent(content);
//		q.setCreateDate(LocalDateTime.now());
//		q.setAuthor(users);
//
//		q.setFilename(fileName); // 파일이름
//		q.setFilepath(projectPath + fileName); // 저장경로,파일이름
//		this.questionRepository.save(q);
//	}
//
//	public Page<Question> getList(int page, String kw) {
//		List<Sort.Order> sorts = new ArrayList<>();
//		sorts.add(Sort.Order.desc("createDate"));
//		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//		Specification<Question> spec = search(kw);
//		return this.questionRepository.findAll(spec, pageable);
//	}
//
//	public void modify(Question question, String subject, String content, MultipartFile file) throws Exception {
//		String projectPath = imgLocation;
//
//        if (file.getOriginalFilename().equals("")){
//            //새 파일이 없을 때
//            question.setFilename(question.getFilename());
//            question.setFilepath(question.getFilepath());
//
//        } else if (file.getOriginalFilename() != null){
//            //새 파일이 있을 때
//            File f = new File(question.getFilepath());
//
//            if (f.exists()) { // 파일이 존재하면
//                f.delete(); // 파일 삭제
//            }
//
//            UUID uuid = UUID.randomUUID();
//            String fileName = uuid + "_" + file.getOriginalFilename();
//            File saveFile = new File(projectPath, fileName);
//            file.transferTo(saveFile);
//
//            question.setFilename(fileName);
//            question.setFilepath(projectPath + fileName);
//        }
//
//        question.setSubject(subject);
//        question.setContent(content);
//        question.setModifyDate(LocalDateTime.now());
//        
//        this.questionRepository.save(question);
//
//         }
//
//	public void delete(Question question) {
//		this.questionRepository.delete(question);
//	}
//}