package project.boot.fideco.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import jakarta.inject.Inject;
import project.boot.fideco.entity.Payment;
import project.boot.fideco.repository.PaymentRepository;

@Service
public class PaymentService {
	@Inject
	private PaymentRepository paymentRepository;
	
	@Transactional
	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}
	
	@Transactional(readOnly = true)
	public List<Payment> findAllPayments() {
	return paymentRepository.findAll();
	}
	
	public boolean verifyPayment(String impUid, String merchantUid) {
		RestTemplate restTemplate = new RestTemplate();
		String url =  "https://api.iamport.kr/payments/" + impUid;
		return true;
	}
}
