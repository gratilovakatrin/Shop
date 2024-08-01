package org.katrin.store.repository;

import org.katrin.store.entity.OrderEntity;
import org.katrin.store.entity.OrderItemEntity;

public interface OrderRepositoryCustom {

    OrderEntity updateWithoutItems(OrderEntity order);
    OrderEntity addItem(int orderId, OrderItemEntity orderItemEntity);
}
