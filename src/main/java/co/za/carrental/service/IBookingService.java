package co.za.carrental.service;

import co.za.carrental.domain.Booking;

import java.util.List;

public interface IBookingService extends IService<Booking, String> {

    List<Booking> getAll();
}
