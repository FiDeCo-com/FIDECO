package project.boot.fideco.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boot.fideco.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByMember_MemberId(String memberId);
}
