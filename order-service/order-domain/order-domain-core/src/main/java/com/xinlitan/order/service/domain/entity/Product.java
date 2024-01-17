package com.xinlitan.order.service.domain.entity;

import com.xinlitan.common.domain.entity.BaseEntity;
import com.xinlitan.common.domain.value_object.Amount;
import com.xinlitan.common.domain.value_object.id.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Amount price;

    public Product(ProductId productId, String name, Amount price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Amount getPrice() {
        return price;
    }
}
