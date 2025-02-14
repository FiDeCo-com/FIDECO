//package project.boot.fideco.repository;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import project.boot.fideco.entity.Question;
//
//public interface QuestionRepository extends JpaRepository<Question, Integer>{
//	
//	Question findBySubject(String subject);
//    Question findBySubjectAndContent(String subject, String content);
//    Page<Question> findAll(Pageable pageable);
//    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
//
//}
