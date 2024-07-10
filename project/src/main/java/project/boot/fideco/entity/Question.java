//package project.boot.fideco.entity;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "QUESTION")
//public class Question {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//
//	private Integer id;
//
//	@Column(length = 200)
//	private String subject;
//
//	@Column(columnDefinition = "TEXT")
//	private String content;
//
//	private LocalDateTime createDate;
//
//	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
//	private List<Answer> answerList;
//
//	@ManyToOne
//	private Users author;
//
//	private LocalDateTime modifyDate;
//
//	@Column(columnDefinition = "integer default 0", nullable = false)
//	private int countview; /* 조회수 */
//
//	private String filepath;/* 파일저장경로 */
//	private String filename;/* 파일이름 */
//}
