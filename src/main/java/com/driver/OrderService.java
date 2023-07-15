package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addPartner(String deliveryPartnerId){
        orderRepository.addPartner(deliveryPartnerId);
    }
    public DeliveryPartner getPartnerById(String partnerId) {

        return orderRepository.getPartner(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId)  {
        List <String> list = new ArrayList<>();
        Set <String> set;
        try {
            set = orderRepository.getOrderIdsByPartnerId(partnerId);
        }catch (Exception e){
            return list;
        }
        if(set == null)
            return list;

        list.addAll(set);

        return list;
    }

    public void addOrderPartnerPair(String orderId, String partnerId)  {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public Order getOrderById(String orderId)  {
        return orderRepository.getOrder(orderId);
    }

    public void addOrder(Order order)  {
        orderRepository.addOrder(order);
    }


    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getUnassignedOrderCount() {
        return orderRepository.getUnassignedOrderCount();
    }


    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }

    public Integer getOrderCountByPartnerId(String partnerId)  {

        DeliveryPartner deliveryPartner = orderRepository.getPartner(partnerId);
        if(deliveryPartner == null)
            return 0;

        return deliveryPartner.getNumberOfOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String partnerId, String time) {

        Set <String> set = orderRepository.getOrderIdsByPartnerId(partnerId);
        Order dummy = new Order("1" , time);

        Integer countOfOrders = 0;

        for(String orderId: set){
            Order order = orderRepository.getOrder(orderId);

            if(order.getDeliveryTime() > dummy.getDeliveryTime())
                countOfOrders++;
        }
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        Set <String> set = orderRepository.getOrderIdsByPartnerId(partnerId);

        if(set == null)
            return "00:00";

        int maxTime = 0;
        for(String orderId: set){
            Order order = orderRepository.getOrder(orderId);
            maxTime = Math.max(maxTime , order.getDeliveryTime());
        }
        String minutes = maxTime % 60 > 10 ? maxTime % 60 + "" : "0" + maxTime % 60;
        String hours = maxTime / 60 > 10 ? maxTime / 60 + "" : "0" + maxTime / 60;

        return hours + ":" + minutes;
    }
}