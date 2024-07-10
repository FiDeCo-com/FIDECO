package project.boot.fideco.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boot.fideco.cart.entity.CartItemEntity;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}
