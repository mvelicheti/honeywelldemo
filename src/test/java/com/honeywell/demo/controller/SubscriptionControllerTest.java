package com.honeywell.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.honeywell.demo.entity.Subscription;
import com.honeywell.demo.service.SubscriptionService;

@SpringBootTest
public class SubscriptionControllerTest {
	
	@Autowired
	SubscriptionController subscriptionController;
	
	@MockBean
	SubscriptionService subscriptionService;
	
	@BeforeEach
    public void setup() {
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription sub1 = new Subscription();
        sub1.setId(1L);
        sub1.setName("JetConnex");
        subscriptions.add(sub1);

        Mockito.when(subscriptionService.getAllSubscriptions()).thenReturn(subscriptions);
    }

    @Test
    public void testGetAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionController.getAllSubscriptions();
        Assertions.assertEquals(1, subscriptions.size());
       Assertions.assertEquals("JetConnex", subscriptions.get(0).getName());
    }

	

}
