package com.athletezone.web.controllerREST;

import com.athletezone.web.dto.CartItemDTO;
import com.athletezone.web.models.Cart;
import com.athletezone.web.models.CartItem;
import com.athletezone.web.models.Product;
import com.athletezone.web.models.User;
import com.athletezone.web.repositories.CartItemRepository;
import com.athletezone.web.repositories.CartRepository;
import com.athletezone.web.repositories.ProductRepository;
import com.athletezone.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class U_cartpageControllerREST {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Long userId) {
        Optional<Cart> cartOpt = Optional.ofNullable(cartRepository.findByUserId(userId));
        if (cartOpt.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        Cart cart = cartOpt.get();
        List<CartItemDTO> items = cartItemRepository.findByCartId(cart.getId()).stream().map(item -> {
            double price = item.getProduct().getPrice();
            int qty = item.getQuantity();
            return CartItemDTO.builder()
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .price(price)
                    .quantity(qty)
                    .subTotal(price * qty)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @RequestBody CartItemDTO cartItemDTO) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Product> productOpt = productRepository.findById(cartItemDTO.getProductId());

        if (userOpt.isEmpty() || productOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User or Product not found.");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userOpt.get());
            cart = (Cart) cartRepository.save(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(productOpt.get());
        cartItem.setQuantity(cartItemDTO.getQuantity());

        cartItemRepository.save(cartItem);
        return ResponseEntity.ok("Product added to cart.");
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        Optional<Cart> cartOpt = Optional.ofNullable(cartRepository.findByUserId(userId));
        if (cartOpt.isEmpty()) {
            return ResponseEntity.ok("Cart already empty.");
        }

        cartItemRepository.deleteAllByCartId(cartOpt.get().getId());
        return ResponseEntity.ok("Cart cleared.");
    }
}
