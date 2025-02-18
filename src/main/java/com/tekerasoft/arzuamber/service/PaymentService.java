package com.tekerasoft.arzuamber.service;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.CreateThreedsPaymentRequestV2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final Options options;

    public PaymentService(Options options) {
        this.options = options;
    }

    public ThreedsInitialize payment(com.tekerasoft.arzuamber.dto.request.CreatePaymentRequest req) {
        try {
            CreatePaymentRequest request = new CreatePaymentRequest();
            request.setLocale(Locale.TR.getValue());
            request.setConversationId("arzu_amber_moda");
            request.setCurrency(Currency.TRY.name());
            request.setInstallment(1);
            request.setBasketId("B67832");
            request.setPaymentChannel(PaymentChannel.WEB.name());
            request.setPaymentGroup(PaymentGroup.PRODUCT.name());

            PaymentCard paymentCard = new PaymentCard();
            paymentCard.setCardHolderName(req.getPaymentCard().getCardHolderName());
            paymentCard.setCardNumber(req.getPaymentCard().getCardNumber());
            paymentCard.setExpireMonth(req.getPaymentCard().getExpireMonth());
            paymentCard.setExpireYear(req.getPaymentCard().getExpireYear());
            paymentCard.setCvc(req.getPaymentCard().getCvc());
            paymentCard.setRegisterCard(0);
            request.setPaymentCard(paymentCard);

            Buyer buyer = new Buyer();
            buyer.setId(req.getBuyer().getId());
            buyer.setName(req.getBuyer().getName());
            buyer.setSurname(req.getBuyer().getSurname());
            buyer.setGsmNumber(req.getBuyer().getGsmNumber());
            buyer.setEmail(req.getBuyer().getEmail());
            buyer.setIdentityNumber(req.getBuyer().getIdentityNumber());
            buyer.setLastLoginDate(req.getBuyer().getLastLoginDate());
            buyer.setRegistrationDate(req.getBuyer().getRegistrationDate());
            buyer.setRegistrationAddress(req.getBuyer().getRegistrationAddress());
            buyer.setIp(req.getBuyer().getIp());
            buyer.setCity(req.getShippingAddress().getCity());
            buyer.setCountry(req.getShippingAddress().getCountry());
            buyer.setZipCode(req.getShippingAddress().getZipCode());
            request.setBuyer(buyer);

            Address shippingAddress = new Address();
            shippingAddress.setContactName(req.getShippingAddress().getContactName());
            shippingAddress.setCity(req.getShippingAddress().getCity());
            shippingAddress.setCountry(req.getShippingAddress().getCountry());
            shippingAddress.setAddress(req.getShippingAddress().getAddress());
            shippingAddress.setZipCode(req.getShippingAddress().getZipCode());
            request.setShippingAddress(shippingAddress);

            Address billingAddress = new Address();
            if(req.getBillingAddress() != null) {
                billingAddress.setContactName(req.getBillingAddress().getContactName());
                billingAddress.setCity(req.getBillingAddress().getCity());
                billingAddress.setCountry(req.getBillingAddress().getCountry());
                billingAddress.setAddress(req.getBillingAddress().getAddress());
                billingAddress.setZipCode(req.getBillingAddress().getZipCode());
                request.setBillingAddress(billingAddress);
            } else {
                request.setBillingAddress(shippingAddress);
            }

            request.setCallbackUrl("https://arzuamber.com/v1-api/v1/api/order/complete-threeds");

            List<BasketItem> basketItems = new ArrayList<>();
            for (com.tekerasoft.arzuamber.dto.request.BasketItem bi : req.getBasketItems()) {
                BasketItem basketI = new BasketItem();
                basketI.setId(bi.getId());
                basketI.setName(bi.getName());
                basketI.setCategory1(bi.getCategory1());
                basketI.setCategory2(bi.getCategory2());
                basketI.setItemType(BasketItemType.PHYSICAL.name());

                // Fiyatı quantity (adet) ile çarpıyoruz
                BigDecimal totalItemPrice = new BigDecimal(bi.getPrice()).multiply(new BigDecimal(bi.getQuantity()));
                basketI.setPrice(totalItemPrice);

                basketItems.add(basketI);
            }

// Tüm ürünlerin toplam fiyatını hesapla
            BigDecimal totalPrice = basketItems.stream()
                    .map(BasketItem::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

// Toplam fiyatı request içine ekleyelim
            request.setPrice(totalPrice);
            request.setPaidPrice(totalPrice);
            request.setBasketItems(basketItems);

            return ThreedsInitialize.create(request,options);

        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating payment request", e);
        }
    }

    public ThreedsPayment completePayment(String paymentId, String conversationId) {
        try {
            CreateThreedsPaymentRequestV2 threedsRequest = new CreateThreedsPaymentRequestV2();
            threedsRequest.setPaymentId(paymentId);
            threedsRequest.setConversationId(conversationId);
            threedsRequest.setLocale(Locale.TR.getValue());

            // 3D Secure Ödeme Tamamlama
            return ThreedsPayment.create(threedsRequest, options);

        } catch (RuntimeException e) {
            throw new RuntimeException("Error completing 3D Secure payment", e);
        }
    }

}
