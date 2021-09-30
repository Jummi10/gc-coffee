package com.example.gccoffee.controller;

import java.util.List;

import com.example.gccoffee.model.OrderItem;

public record CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
}
