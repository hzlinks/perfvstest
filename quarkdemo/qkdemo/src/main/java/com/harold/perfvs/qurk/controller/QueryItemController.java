package com.harold.perfvs.qurk.controller;


import com.harold.perfvs.qurk.domain.ItemInfoBo;
import com.harold.perfvs.qurk.service.ItemOrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/query")
public class QueryItemController {

    @Inject
    ItemOrderService itemOrderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ItemInfoBo queryItem(@RestQuery Long itemId) {
        return itemOrderService.getItemWithStock(itemId);
    }

}
