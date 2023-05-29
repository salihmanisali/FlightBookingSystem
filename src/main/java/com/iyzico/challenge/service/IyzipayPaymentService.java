package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.PaymentDto;
import com.iyzico.challenge.entity.Seat;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IyzipayPaymentService {

  private final Options options;

  public String createPaymentRequest(final List<Seat> seats, final PaymentDto paymentDto) {
    BigDecimal price = calculatePrice(seats);

    CreatePaymentRequest request = getCreatePaymentRequest(price);

    request.setPaymentCard(getPaymentCard(paymentDto));
    request.setBuyer(getBuyer());
    request.setShippingAddress(getAddress());
    request.setBillingAddress(getAddress());
    request.setBasketItems(getBasketItems(seats));

    Payment payment = Payment.create(request, options);

    log.info("Iyzipay payment completed: {}", payment);

    return payment.getStatus();
  }

  private static CreatePaymentRequest getCreatePaymentRequest(BigDecimal price) {
    CreatePaymentRequest request = new CreatePaymentRequest();
    request.setLocale(Locale.TR.getValue());
    request.setConversationId(UUID.randomUUID().toString());
    request.setPrice(price);
    request.setPaidPrice(price);
    request.setCurrency(Currency.TRY.name());
    request.setInstallment(1);
    request.setBasketId("B67832");
    request.setPaymentChannel(PaymentChannel.WEB.name());
    request.setPaymentGroup(PaymentGroup.PRODUCT.name());
    return request;
  }

  private static PaymentCard getPaymentCard(PaymentDto paymentDto) {
    PaymentCard paymentCard = new PaymentCard();
    paymentCard.setCardHolderName(paymentDto.getCardHolderName());
    paymentCard.setCardNumber(paymentDto.getCardNumber());
    paymentCard.setExpireMonth(paymentDto.getCardExpireMonth());
    paymentCard.setExpireYear(paymentDto.getCardExpireYear());
    paymentCard.setCvc(paymentDto.getCardCvc());
    paymentCard.setRegisterCard(0);
    return paymentCard;
  }

  private static Buyer getBuyer() {
    Buyer buyer = new Buyer();
    buyer.setId("BY789");
    buyer.setName("John");
    buyer.setSurname("Doe");
    buyer.setGsmNumber("+905350000000");
    buyer.setEmail("email@email.com");
    buyer.setIdentityNumber("74300864791");
    buyer.setLastLoginDate("2015-10-05 12:43:35");
    buyer.setRegistrationDate("2013-04-21 15:12:09");
    buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
    buyer.setIp("85.34.78.112");
    buyer.setCity("Istanbul");
    buyer.setCountry("Turkey");
    buyer.setZipCode("34732");
    return buyer;
  }

  private static Address getAddress() {
    Address billingAddress = new Address();
    billingAddress.setContactName("Jane Doe");
    billingAddress.setCity("Istanbul");
    billingAddress.setCountry("Turkey");
    billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
    billingAddress.setZipCode("34742");
    return billingAddress;
  }

  private static List<BasketItem> getBasketItems(List<Seat> seats) {
    List<BasketItem> basketItems = new ArrayList<>();
    seats.forEach(seat -> {
      BasketItem basketItem = new BasketItem();
      basketItem.setId(seat.getFlight().getName() + "-Seat" + seat.getSeatNumber());
      basketItem.setName("Flight Ticket");
      basketItem.setCategory1("Travel");
      basketItem.setItemType(BasketItemType.VIRTUAL.name());
      basketItem.setPrice(seat.getSeatPrice());
      basketItems.add(basketItem);
    });
    return basketItems;
  }

  private BigDecimal calculatePrice(final List<Seat> seats) {
    return seats.stream()
      .map(Seat::getSeatPrice)
      .filter(Objects::nonNull)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
