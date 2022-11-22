package com.poly.dao.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.poly.dao.PaypalService;
import com.poly.enums.PaypalPaymentIntent;
import com.poly.enums.PaypalPaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalServiceImpl implements PaypalService {
    @Autowired
    private APIContext apiContext;

    @Override
    public Payment createPayment(Double total, String currency, PaypalPaymentMethod method,
                                 PaypalPaymentIntent intent, String description, String cancelUrl,
                                 String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        String totalString=df.format(total);
        if (!totalString.contains(".")) {
            totalString=totalString+".00";
        }
        amount.setTotal(totalString);
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        List transactions = new ArrayList<>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());
        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
