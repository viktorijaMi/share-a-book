package mk.ukim.finki.emt.ordermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.model.Order;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItem;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.service.OrderService;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> findAll() {
        return this.orderService.findAll();
    }

    @GetMapping("/new-order")
    public OrderId createNewOrder() {
        return this.orderService.createNewOrder();
    }

    @GetMapping("/items/{id}")
    public Set<OrderItem> findAllByOrderId(@PathVariable OrderId id){
        return this.orderService.findAllOrderItemsById(id);
    }

    @GetMapping("/total/{id}")
    public Money getTotalPrice(@PathVariable OrderId id) {
        return this.orderService.findById(id).total();
    }

    @GetMapping("/change-total-currency/{id}")
    public Money getTotalPriceWithDifferentCurrency(@PathVariable OrderId id, @RequestParam Currency currency) {
        return orderService.changeTotalCurrency(id, currency);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable OrderId id) {
        return this.orderService.findById(id);
    }


    @PostMapping("/place-order/{orderId}")
    public OrderId placeOrder(@PathVariable OrderId orderId, @RequestParam String currency) {
        return this.orderService.placeOrder(orderId,Currency.valueOf(currency));
    }

    @GetMapping("/get-currency")
    public List<Currency> getCurrencies() {
        List<Currency> currencies =  Arrays.asList(Currency.values().clone());
        return currencies;
    }


    @PostMapping("/add/{id}")
    public OrderItem addItem(@PathVariable OrderId id, @RequestBody OrderItemForm orderItemForm) {
        return this.orderService.addItem(id, orderItemForm);
    }

    @PostMapping("/increase-qty/{orderId}")
    public void increaseQuantity(@PathVariable OrderId orderId, @RequestBody OrderItemId orderItemId) {
        this.orderService.increaseQuantity(orderId, orderItemId);
    }

    @PostMapping("/decrease-qty/{orderId}")
    public void decreaseQuantity(@PathVariable OrderId orderId, @RequestBody OrderItemId orderItemId) {
        this.orderService.decreaseQuantity(orderId, orderItemId);
    }


    @PostMapping("/delete")
    public void deleteItem(@RequestParam OrderId id, @RequestParam OrderItemId orderItemId) {
        this.orderService.deleteItem(id, orderItemId);
    }

}
