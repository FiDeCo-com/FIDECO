package project.boot.fideco.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String merchant_uid;
	private String name;
	private Integer amount;
	private Long order_no;
	private Long product_id;
	private Long member_id;
	private Long cart_id;
	
	
	private LocalDateTime paymenttime;
	
	
}
