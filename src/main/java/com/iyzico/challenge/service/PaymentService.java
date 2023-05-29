package com.iyzico.challenge.service;


import com.iyzico.challenge.constants.ErrorCode;
import com.iyzico.challenge.constants.SeatStatus;
import com.iyzico.challenge.dto.PaymentDto;
import com.iyzico.challenge.dto.PaymentResponseDto;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.exception.PaymentException;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzipay.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SeatService seatService;
    private final FlightService flightService;
    private final IyzipayPaymentService iyzipayPaymentService;

    @Transactional(rollbackFor = PaymentException.class)
    public PaymentResponseDto createPayment(final String flightId, final PaymentDto paymentDto) {
        Flight flight = flightService.getFlight(flightId);
        List<Seat> seats = new ArrayList<>();
        for (String seatNumber : paymentDto.getSeatNumbers()) {
            try {
                Seat seat = seatService.getSeat(flight, seatNumber);
                if (!SeatStatus.AVAILABLE.equals(seat.getSeatStatus())) {
                    throw new PaymentException(ErrorCode.SEAT_ALREADY_SOLD);
                }
                seats.add(seat);
            } catch (Exception ex) {
                log.error("Seat already sold for seat number: " + seatNumber);
                throw new PaymentException(ErrorCode.SEAT_ALREADY_SOLD);
            }
        }

        String paymentStatus = iyzipayPaymentService.createPaymentRequest(seats, paymentDto);

        if (Status.SUCCESS.getValue().equals(paymentStatus)) {
            seatService.updateSeatStatus(seats, SeatStatus.SOLD);
            savePayment(flight, seats);
            log.info("Payment successful for flight: " + flight.getIdentifier());
        } else {
            log.error("Payment failed for flight: " + flight.getIdentifier());
            throw new PaymentException(ErrorCode.PAYMENT_FAILED);
        }

        return getPaymentResponseDto(paymentDto, flight);
    }

    private PaymentResponseDto getPaymentResponseDto(PaymentDto paymentDto, Flight flight) {
        PaymentResponseDto dto=new PaymentResponseDto();
        dto.setFlight(flightService.convertToDto(flight));
        dto.getSeatNumbers().addAll(paymentDto.getSeatNumbers());
        dto.setPaymentStatus(Status.SUCCESS.getValue());
        return dto;
    }

    private void savePayment(final Flight flight, final List<Seat> seats) {
        for (Seat seat : seats) {
            Payment payment = new Payment();
            payment.setPrice(seat.getSeatPrice());
            payment.setFlight(flight);
            payment.setSeat(seat);
            paymentRepository.save(payment);
            log.info("Payment saved successfully!");
        }
    }


}
