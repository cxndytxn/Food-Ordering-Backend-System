package com.xinlitan.order.service.domain.value_object;

import com.xinlitan.common.domain.value_object.id.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
