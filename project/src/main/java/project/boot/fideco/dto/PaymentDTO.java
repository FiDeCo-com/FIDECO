package project.boot.fideco.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentDTO {

	private Long id;

    private String merchant_uid;
    private String name;
    private int amount;
    private Long order_no;
    private Long product_id;
    private Long member_id;
    private Long cart_id;
    private LocalDateTime paymenttime;
}
