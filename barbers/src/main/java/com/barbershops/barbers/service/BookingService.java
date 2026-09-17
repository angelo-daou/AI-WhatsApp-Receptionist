package com.barbershops.barbers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Barber;
import com.barbershops.barbers.entity.Booking;
import com.barbershops.barbers.entity.Client;
import com.barbershops.barbers.entity.Services;
import com.barbershops.barbers.entity.Shop;
import com.barbershops.barbers.repository.BookingRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);

        if (booking.isPresent()) {
            return booking.get();
        }
        return null;
    }

    public Booking createBooking(Booking booking) {
        Barber barber = booking.getBarber();
        Shop shop = booking.getShop();
        Client client = booking.getClient();
        Services service = booking.getService();

        List<Booking> bookings = bookingRepository.findAll();

        if (!(barber.getShop().getShopId().equals(shop.getShopId())
                && client.getShop().getShopId().equals(shop.getShopId())
                && service.getShop().getShopId().equals(shop.getShopId()))) {
            return null;
        }
        if (!booking.getStartTime().isBefore(booking.getEndTime())) {
            return null;
        }

        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBarber().getBarberId().equals(barber.getBarberId())) {
                if (bookings.get(i).getStartTime().isBefore(booking.getEndTime())
                        && bookings.get(i).getEndTime().isAfter(booking.getStartTime())) { // if overlap
                    return null;
                } // oldst < newEt && oldEt > newSt, if true then overlap
            } // checking overlap
        }
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Integer id, Booking newBooking) {
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();

            Barber barber = newBooking.getBarber();
            Shop shop = newBooking.getShop();
            Client client = newBooking.getClient();
            Services service = newBooking.getService();
            if (!(barber.getShop().getShopId().equals(shop.getShopId())
                    && client.getShop().getShopId().equals(shop.getShopId())
                    && service.getShop().getShopId().equals(shop.getShopId()))) {
                return null;
            }

            List<Booking> bookings = bookingRepository.findAll();
            for (int i = 0; i < bookings.size(); i++) {
                if (bookings.get(i).getBarber().getBarberId().equals(barber.getBarberId())) {
                    if (bookings.get(i).getStartTime().isBefore(booking.getEndTime())
                            && bookings.get(i).getEndTime().isAfter(booking.getStartTime())) { // if overlap
                        return null;
                    } // oldst < newEt && oldEt > newSt, if true then overlap
                } // checking overlap
            }

            booking.setBarber(newBooking.getBarber());
            booking.setClient(newBooking.getClient());
            booking.setEndTime(newBooking.getEndTime());
            booking.setService(newBooking.getService());
            booking.setShop(newBooking.getShop());
            booking.setStartTime(newBooking.getStartTime());
            return bookingRepository.save(booking);
        }
        return null;
    }

    public boolean deleteBooking(Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
