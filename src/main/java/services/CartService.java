package services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.Cart;
import model.CartItem;
import model.Customer;
import model.Item;

@ApplicationScoped
public class CartService {

    @Transactional
    public void addToCart(Long customerId, Long itemId, int quantity) {

        Cart cart = Cart.find("customer.id", customerId).firstResult();

        if (cart == null) {
            cart = new Cart();
            cart.customer = Customer.findById(customerId);
            cart.persist();
        }

        CartItem existing = CartItem.find("cart = ?1 and item.id = ?2", cart, itemId)
                                    .firstResult();

        if (existing != null) {
            existing.quantity += quantity;
        } else {
            CartItem ci = new CartItem();
            ci.cart = cart;
            ci.item = Item.findById(itemId);
            ci.quantity = quantity;
            ci.persist();
        }
    }
    
    public List<CartItem> getCartItems(Long customerId) {
        Cart cart = Cart.find("customer.id", customerId).firstResult();
        if (cart == null) return List.of();

        return CartItem.find("cart", cart).list();
    }
    
    @Transactional
    public void removeFromCart(Long cartItemId) {
        CartItem cartItem = CartItem.findById(cartItemId);

        cartItem.delete();
    }
}