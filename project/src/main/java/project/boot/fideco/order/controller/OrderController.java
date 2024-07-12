package project.boot.fideco.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.boot.fideco.order.entity.OrderEntity;
import project.boot.fideco.order.service.OrderService;
import project.boot.fideco.cart.service.CartService;
import project.boot.fideco.provider.JwtProvider;
import project.boot.fideco.cart.entity.CartEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final JwtProvider jwtProvider;

    @Value("${secret-key}")
    private String secretKey;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, JwtProvider jwtProvider) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Map<String, Long>> createOrder(HttpServletRequest request, @RequestParam("orderAmount") int orderAmount) {
        String token = getTokenFromCookies(request.getCookies());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String memberId = jwtProvider.validate(token);
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        Long id = claims.get("id", Long.class);

        CartEntity cart = cartService.getCartByMemberId(memberId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Long cartId = cart.getCartId();

        Long orderId = orderService.createOrder(cartId, id, orderAmount);

        // orderId 반환
        Map<String, Long> response = new HashMap<>();
        response.put("orderId", orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public String getOrderById(@PathVariable("orderId") Long orderId, Model model) {
        OrderEntity order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "order/orderDetail";
    }

    @GetMapping("/list")
    public String getAllOrders(Model model) {
        List<OrderEntity> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/member/{memberId}")
    public String getOrdersByMemberId(@PathVariable("memberId") String memberId, Model model) {
        List<OrderEntity> orders = orderService.getOrdersByMemberId(memberId);
        model.addAttribute("orders", orders);
        return "order/memberOrders";
    }

    @GetMapping("/update/{orderId}")
    public String updateOrderForm(@PathVariable("orderId") Long orderId, Model model) {
        OrderEntity order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "order/updateOrder";
    }

    @PostMapping("/update/{orderId}")
    public String updateOrder(@PathVariable("orderId") Long orderId, @RequestParam("orderAmount") int orderAmount) {
        OrderEntity order = orderService.getOrderById(orderId);
        order.setOrderAmount(orderAmount);
        orderService.saveOrder(order);
        return "redirect:/orders/list";
    }

    @GetMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders/list";
    }

    @DeleteMapping("/cancel/{orderId}")
    @ResponseBody
    public ResponseEntity<Void> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    private String extractMemberIdFromRequest(HttpServletRequest request) {
        String jwt = getTokenFromCookies(request.getCookies());
        if (jwt == null) {
            return null;
        }
        return jwtProvider.validate(jwt);
    }

    private String getTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
