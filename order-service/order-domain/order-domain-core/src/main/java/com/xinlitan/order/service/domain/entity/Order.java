package com.xinlitan.order.service.domain.entity;


import com.xinlitan.common.domain.entity.RootEntity;
import com.xinlitan.common.domain.logging.DomainLogger;
import com.xinlitan.common.domain.value_object.Amount;
import com.xinlitan.common.domain.value_object.OrderStatus;
import com.xinlitan.common.domain.value_object.id.CustomerId;
import com.xinlitan.common.domain.value_object.id.OrderId;
import com.xinlitan.common.domain.value_object.id.RestaurantId;
import com.xinlitan.order.service.domain.exception.OrderDomainException;
import com.xinlitan.order.service.domain.value_object.Address;
import com.xinlitan.order.service.domain.value_object.OrderItemId;
import com.xinlitan.order.service.domain.value_object.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends RootEntity<OrderId> {

    private static final String DOMAIN_NAME = "Order Service";

    private final DomainLogger domainLogger;

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final Address deliveryAddress;
    private final Amount amount;
    private final List<OrderItem> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrices();
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            String error = "Order is not in correct state for initialization.";
            domainLogger.error(DOMAIN_NAME, error);
            throw new OrderDomainException(error);
        }
    }

    private void validateTotalPrice() {
        if (amount == null || !amount.isGreaterThanZero()) {
            String error = "Total price must be greater than zero.";
            domainLogger.error(DOMAIN_NAME, error);
            throw new OrderDomainException(error);
        }
    }

    private void validateItemsPrices() {
        Amount totalAmount = items.stream().map(item -> {
            validateItemPrice(item);
            return item.getSubtotal();
        }).reduce(Amount.ZERO, Amount::add);

        if (!amount.equals(totalAmount)) {
            String error = "Total price: " + amount + "does not equal to the total amount of ordered items: " + totalAmount;
            domainLogger.error(DOMAIN_NAME, error);
            throw new OrderDomainException(error);
        }
    }

    private void validateItemPrice(OrderItem item) {
        if (!item.isPriceValid()) {
            String error = "Item price: " + amount.getAmount() + "is not valid for product: " + item.getProduct().getId().getValue();
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem item : items) {
            item.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        domainLogger = builder.domainLogger;
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        amount = builder.amount;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Amount getAmount() {
        return amount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private DomainLogger domainLogger;
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private Address deliveryAddress;
        private Amount amount;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder domainLogger(DomainLogger val) {
            domainLogger = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(Address val) {
            deliveryAddress = val;
            return this;
        }

        public Builder amount(Amount val) {
            amount = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
