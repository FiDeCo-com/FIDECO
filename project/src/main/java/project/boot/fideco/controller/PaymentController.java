package project.boot.fideco.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import project.boot.fideco.dto.PaymentDTO;
import project.boot.fideco.entity.Payment;
import project.boot.fideco.service.PaymentService;

@Controller
@RequiredArgsConstructor
public class PaymentController {

	@Inject
	private final PaymentService paymentService;

	@GetMapping("/PaymentInsert")
	public String insert() {
		return "./payment/payment_insert";
	}

	@PostMapping("/processPayment")
	public String processPayment(@ModelAttribute PaymentDTO paymentDTO, Model model) {
		//paymenttime 현재시간으로 변경
		paymentDTO.setPaymenttime(LocalDateTime.now());
		
		Payment payment = new Payment();
		payment.setMerchant_uid(paymentDTO.getMerchant_uid());
		payment.setName(paymentDTO.getName());
		payment.setAmount(paymentDTO.getAmount());
		payment.setOrder_no(paymentDTO.getOrder_no());
		payment.setProduct_id(paymentDTO.getProduct_id());
		payment.setMember_id(paymentDTO.getMember_id());
		payment.setCart_id(paymentDTO.getCart_id());
		payment.setPaymenttime(paymentDTO.getPaymenttime());

		paymentService.savePayment(payment);
		model.addAttribute("list", paymentService.findAllPayments());

		return "./payment/payment";
	}

	@GetMapping("/paymentSuccess")
	public String success(@RequestParam Map<String, String> params, Model model) {
		//map을 쓰는 이유는 @RequestParam을 파라미터마다 각각 해줄 수 없기 때문.
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setMerchant_uid(params.get("merchant_uid"));
		paymentDTO.setName(params.get("name"));
		// amount를 int로 변환
		try {
			paymentDTO.setAmount(Integer.parseInt(params.get("amount")));
		} catch (NumberFormatException e) {
			// 변환 실패 시 기본값 설정 또는 오류 처리
			paymentDTO.setAmount(0);
		}

		// Long 타입 필드들을 변환
		try {
			if (params.get("order_no") != null) {
				paymentDTO.setOrder_no(Long.parseLong(params.get("order_no")));
			}
			if (params.get("product_id") != null) {
				paymentDTO.setProduct_id(Long.parseLong(params.get("product_id")));
			}
			if (params.get("member_id") != null) {
				paymentDTO.setMember_id(Long.parseLong(params.get("member_id")));
			}
			if (params.get("cart_id") != null) {
				paymentDTO.setCart_id(Long.parseLong(params.get("cart_id")));
			}
		} catch (NumberFormatException e) {
			// 변환 실패 시 로그 기록 또는 다른 방식의 오류 처리
			System.err.println("Error parsing Long values: " + e.getMessage());
		}
		paymentDTO.setPaymenttime(LocalDateTime.now());
		
		model.addAttribute("paymentDTO", paymentDTO);
		return "./payment/payment_success";
	}
}
