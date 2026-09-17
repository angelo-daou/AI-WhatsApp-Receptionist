package com.barbershops.barbers.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbershops.barbers.entity.Booking;
import com.barbershops.barbers.service.BookingService;

@RequestMapping("/shop/barber/booking")
@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Integer id) {
        return bookingService.getBooking(id);
    }

    @PostMapping
    public Booking postBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{id}")
    public Booking putBooking(@PathVariable Integer id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBooking(@PathVariable Integer id) {
        return bookingService.deleteBooking(id);
    }
}
