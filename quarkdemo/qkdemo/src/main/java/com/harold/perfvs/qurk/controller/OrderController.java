package com.harold.perfvs.qurk.controller;

import com.harold.perfvs.qurk.service.ItemOrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/create")
public class OrderController {

    @Inject
    ItemOrderService itemOrderService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean createOrder(@RestQuery Long itemId,@RestQuery Long buyerId, @RestQuery Integer quality) {
        return itemOrderService.createOrder(itemId,buyerId,quality);
    }
}
