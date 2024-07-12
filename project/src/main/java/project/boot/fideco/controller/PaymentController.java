package project.boot.fideco.controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import project.boot.fideco.dto.PaymentDTO;
import project.boot.fideco.entity.PaymentStatus;
import project.boot.fideco.member.entity.MemberEntity;
import project.boot.fideco.order.entity.OrderEntity;
import project.boot.fideco.order.service.OrderService;
import project.boot.fideco.service.PaymentService;

@Controller
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/payment/{id}")
	public String showPaymentDetail(@PathVariable Long id, Model model) {
		PaymentDTO paymentDTO = paymentService.getPaymentDTOById(id);
		OrderEntity order = orderService.getOrderById(paymentDTO.getOrder_id());
		MemberEntity member = order.getMember();

		model.addAttribute("paymentDTO", paymentDTO);
		model.addAttribute("order", order);
		model.addAttribute("member", member);

		return "order/orderDetail";
	}

	@GetMapping("/payment/paymentSuccess")
	public String paymentSuccess(@RequestParam(name = "merchant_uid") String merchantUid,
			@RequestParam(name = "order_id") Long orderId, @RequestParam(name = "amount") int amount,
			@RequestParam(name = "paymentstatus") String paymentStatus,
			@RequestParam(name = "payment_date") String paymentDate, Model model) {

		logger.info("merchant_uid: " + merchantUid);
		logger.info("order_id: " + orderId);
		logger.info("amount: " + amount);
		logger.info("paymentstatus: " + paymentStatus);
		logger.info("payment_date: " + paymentDate);

		// DB에 결제 데이터 저장
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setMerchantUid(merchantUid);
		paymentDTO.setOrder_id(orderId);
		paymentDTO.setAmount(amount);
		paymentDTO.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
		
		// OffsetDateTime을 사용하여 문자열을 파싱
		DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		OffsetDateTime offsetDateTime = OffsetDateTime.parse(paymentDate, formatter);
		LocalDateTime dateTime = offsetDateTime.toLocalDateTime();
		paymentDTO.setPaymentDate(dateTime);
		
		paymentService.savePayment(paymentDTO);

		// 데이터를 모델에 추가
		model.addAttribute("merchant_uid", merchantUid);
		model.addAttribute("order_id", orderId);
		model.addAttribute("amount", amount);
		model.addAttribute("paymentstatus", paymentStatus);
		 // 포맷된 날짜 문자열을 모델에 추가
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    String formattedPaymentDate = dateTime.format(outputFormatter);
	    model.addAttribute("formattedPaymentDate", formattedPaymentDate);

		logger.info("model: " + model);

		// payment_success.html을 반환
		return "payment/payment_success";
	}
}
