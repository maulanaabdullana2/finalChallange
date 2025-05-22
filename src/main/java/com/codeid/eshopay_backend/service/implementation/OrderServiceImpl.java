package com.codeid.eshopay_backend.service.implementation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.codeid.eshopay_backend.model.dto.OrderDto;
import com.codeid.eshopay_backend.model.entity.Bank;
import com.codeid.eshopay_backend.model.entity.CartItems;
import com.codeid.eshopay_backend.model.entity.Location;
import com.codeid.eshopay_backend.model.entity.Order;
import com.codeid.eshopay_backend.model.entity.OrderDetail;
import com.codeid.eshopay_backend.model.entity.OrderDetailId;
import com.codeid.eshopay_backend.model.entity.Product;
import com.codeid.eshopay_backend.model.entity.User;
import com.codeid.eshopay_backend.repository.BankRepository;
import com.codeid.eshopay_backend.repository.CartItemRepository;
import com.codeid.eshopay_backend.repository.OrderRepository;
import com.codeid.eshopay_backend.repository.productRepository;
import com.codeid.eshopay_backend.repository.userRepository;
import com.codeid.eshopay_backend.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;
    public final userRepository userRepository;
    public final BankRepository bankRepository;
    public final productRepository productRepository;
    private final CartItemRepository cartItemRepository;


    public static OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderNo(order.getOrderNo())
                .orderDate(order.getOrderDate())
                .requiredDate(order.getRequiredDate())
                .shippedDate(order.getShippedDate())
                .freight(order.getFreight())
                .shipName(order.getShipName())
                .totalDiscount(order.getTotalDiscount())
                .totalAmount(order.getTotalAmount())
                .paymentType(order.getPaymentType())
                .cardNo(order.getCardNo())
                .transacNo(order.getTransacNo())
                .transacDate(order.getTransacDate())
                .refNo(order.getRefNo())
                .userId(order.getUser().getUserId())
                .locationId(order.getLocation().getLocationId())
                .bankCode(order.getBank().getBankCode())
                .build();
    }

    public static Order mapToEntity(OrderDto orderDto) {
        return Order.builder()
                .orderId(orderDto.getOrderId())
                .orderNo(orderDto.getOrderNo())
                .orderDate(orderDto.getOrderDate())
                .requiredDate(orderDto.getRequiredDate())
                .shippedDate(orderDto.getShippedDate())
                .freight(orderDto.getFreight())
                .shipName(orderDto.getShipName())
                .totalDiscount(orderDto.getTotalDiscount())
                .totalAmount(orderDto.getTotalAmount())
                .paymentType(orderDto.getPaymentType())
                .CardNo(orderDto.getCardNo())
                .transacNo(orderDto.getTransacNo())
                .transacDate(orderDto.getTransacDate())
                .refNo(orderDto.getRefNo())
                .user(User.builder().userId(orderDto.getUserId()).build())
                .location(Location.builder().locationId(orderDto.getLocationId()).build())
                .bank(Bank.builder().bankCode(orderDto.getBankCode()).build())
                .build();
    }

    @Override
    public List<OrderDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public OrderDto findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public OrderDto save(OrderDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public OrderDto update(Long id, OrderDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

   @Override
public OrderDto createOrder(OrderDto orderDto, Long userId) {
   
    Order order = mapToEntity(orderDto);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));


    order.setUser(user);

    Bank bank = bankRepository.findById(orderDto.getBankCode())
        .orElseThrow(() -> new RuntimeException("Bank not found"));
    order.setBank(bank);

    List<CartItems> cartItems = cartItemRepository.findByCartUserUserId(userId);

    List<OrderDetail> orderEntities = new ArrayList<>();

    for (CartItems cartItem : cartItems) {
         Product product = cartItem.getProduct();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        OrderDetailId orderDetailId = new OrderDetailId();
        orderDetailId.setOrderId(order.getOrderId()); 
        orderDetailId.setProductId(product.getProductId());
        orderDetail.setId(orderDetailId);
        orderDetail.setPrice(cartItem.getUnitPrice());
        orderDetail.setQuantity(cartItem.getQuantity());
        orderDetail.setDiscount(cartItem.getDiscount());
        orderEntities.add(orderDetail);
    }

    // //delet cart nya kalo udh di create
    // cartItemRepository.deleteAllByCartUserUserId(userId);

    order.setOrderDetails(orderEntities);
    Order savedOrder = orderRepository.save(order);

    return mapToDto(savedOrder);
}

}
