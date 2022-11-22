package com.poly.dao;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.poly.enums.PaypalPaymentIntent;
import com.poly.enums.PaypalPaymentMethod;

public interface PaypalService {
    public Payment createPayment(
            Double total,
            String currency,
            PaypalPaymentMethod method,
            PaypalPaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException;
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
