package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Outlet;
import com.tw.joi.delivery.seedData.SeedData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OutletService {

    private static final List<Outlet> outlets = SeedData.outlets;

    public Outlet getOutletInfoForOutletID(String outletId) {
        return outlets.stream()
                .filter(outlet -> Objects.equals(outlet.getOutletId(), outletId))
                .findFirst()
                .orElse(null);
    }
}
