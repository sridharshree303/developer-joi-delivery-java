package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.Outlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OutletServiceTest {

    @InjectMocks
    private OutletService outletService;

    @Test
    void fetchOutletObject_forBlankParam(){
        String outletId = "";
        Outlet outlet = outletService.getOutletInfoForOutletID(outletId);

        Assertions.assertNull(outlet);
    }

    @Test
    void fetchOutletObject_forValidOutletID(){
        String outletId = "store101";
        String outletName = "Fresh Picks";

        Outlet actual = outletService.getOutletInfoForOutletID(outletId);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(outletName, actual.getName());
        Assertions.assertEquals(outletId, actual.getOutletId());
    }
}
