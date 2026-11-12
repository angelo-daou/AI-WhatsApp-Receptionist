package com.barbershops.barbers.service;

import com.barbershops.barbers.repository.ClientRepository;
import java.time.LocalDateTime;
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
    private final ClientRepository clientRepository;
    private final BookingRepository bookingRepository;
    private final BarberService barberService;
    private final ClientService clientService;
    private final ServiceService serviceService;

    public BookingService(BookingRepository bookingRepository, BarberService barberService, ClientService clientService,
            ServiceService serviceService, ClientRepository clientRepository) {
        this.bookingRepository = bookingRepository;
        this.barberService = barberService;
        this.clientService = clientService;
        this.serviceService = serviceService;
        this.clientRepository = clientRepository;
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

    public Boolean checkAvailability(Integer barberId, LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.isBefore(endTime)) {
            return false;
        }
        List<Booking> bookings = bookingRepository.findAll();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBarber().getBarberId().equals(barberId)) {
                if (bookings.get(i).getStartTime().isBefore(endTime)
                        && bookings.get(i).getEndTime().isAfter(startTime)) { // if overlap
                    return false;
                } // oldst < newEt && oldEt > newSt, if true then overlap
            } // checking overlap
        }
        return true; // available
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

    public Booking createBooking(Integer barberId, Integer serviceId, String name, String phoneNum,
            LocalDateTime startTime) {
        Barber barber = barberService.getBarber(barberId);
        Shop shop = barber.getShop();
        Client client = clientRepository.findByPhoneNumAndShop(phoneNum, shop)
                .orElse(clientService.createClient(name, phoneNum, shop));
        Services service = serviceService.getService(serviceId);

        Booking booking = new Booking(shop, barber, service, client, startTime,
                startTime.plusMinutes(service.getDurationMinutes()));

        List<Booking> bookings = bookingRepository.findAll();

        if (!(client.getShop().getShopId().equals(shop.getShopId())
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

            if (!newBooking.getStartTime().isBefore(newBooking.getEndTime())) {
                return null;
            }

            List<Booking> bookings = bookingRepository.findAll();
            for (int i = 0; i < bookings.size(); i++) {
                if (bookings.get(i).getBookingId().equals(id)) {
                    continue; // skip the booking being updated
                }
                if (bookings.get(i).getBarber().getBarberId().equals(barber.getBarberId())) {
                    if (bookings.get(i).getStartTime().isBefore(newBooking.getEndTime())
                            && bookings.get(i).getEndTime().isAfter(newBooking.getStartTime())) { // if overlap
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
