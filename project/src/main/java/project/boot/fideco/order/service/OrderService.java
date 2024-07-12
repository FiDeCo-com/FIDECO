package project.boot.fideco.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boot.fideco.order.entity.OrderEntity;
import project.boot.fideco.order.repository.OrderRepository;
import project.boot.fideco.cart.entity.CartEntity;
import project.boot.fideco.cart.repository.CartRepository;
import project.boot.fideco.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
 
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long createOrder(Long cartId, Long memberId, int orderAmount) {
        OrderEntity order = new OrderEntity();
        order.setCart(cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Invalid cart ID")));
        order.setMember(memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID")));
        order.setOrderAmount(orderAmount);
        OrderEntity savedOrder = orderRepository.save(order);

        // 장바구니 비우기 기능 제거

        return savedOrder.getOrderId();
    }

    @Transactional(readOnly = true)
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
    }

    @Transactional(readOnly = true)
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByMemberId(String memberId) {
        return orderRepository.findByMember_MemberId(memberId);
    }

    @Transactional
    public void saveOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public Optional<CartEntity> getCartByMemberId(String memberId) {
        return cartRepository.findByMemberId(memberId);
    }

  
}
