package project.boot.fideco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.boot.fideco.dto.PaymentDTO;
import project.boot.fideco.entity.Payment;
import project.boot.fideco.member.repository.MemberRepository;
import project.boot.fideco.order.repository.OrderRepository;
import project.boot.fideco.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MemberRepository memberRepository;

	public PaymentDTO getPaymentDTOById(Long paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new IllegalArgumentException("Payment not found for id: " + paymentId));

		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setMerchantUid(payment.getMerchantUid());
		paymentDTO.setOrder_id(payment.getOrder().getOrderId());
		paymentDTO.setAmount(payment.getOrderAmount());
		paymentDTO.setPaymentStatus(payment.getPaymentStatus());
		paymentDTO.setPaymentDate(payment.getPaymentDate());

		return paymentDTO;
	}

	@Transactional
	public void updatePayment(Long id, PaymentDTO paymentDTO) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Payment not found for id: " + id));
		payment.setPaymentStatus(paymentDTO.getPaymentStatus());
		payment.setPaymentDate(paymentDTO.getPaymentDate());
		paymentRepository.save(payment);
	}

	@Transactional
	public void deletePayment(Long id) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Payment not found for id: " + id));
		paymentRepository.delete(payment);
	}

	@Transactional
	public void savePayment(PaymentDTO paymentDTO) {
		Payment payment = new Payment();
		payment.setMerchantUid(paymentDTO.getMerchantUid());
		payment.setOrder(orderRepository.findById(paymentDTO.getOrder_id()).orElse(null));
		payment.setOrderAmount(paymentDTO.getAmount());
		payment.setPaymentStatus(paymentDTO.getPaymentStatus());
		payment.setPaymentDate(paymentDTO.getPaymentDate());

		paymentRepository.save(payment);
	}
}
