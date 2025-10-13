package co.za.carrental.service.impl;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.Car;
import co.za.carrental.repository.BookingRepository;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements ICarService {

    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, BookingRepository bookingRepository) {
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> findByCarId(String carId) {
        return carRepository.findByCarId(carId);
    }

    @Override
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public void deleteByCarId(String carId) {
        Car car = carRepository.findByCarId(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found."));
        bookingRepository.deleteAll(bookingRepository.findByCar(car));
        carRepository.deleteByCarId(carId);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        List<Car> allCars = carRepository.findAll();

        return allCars.stream()
                .filter(car -> {
                    List<Booking> bookings = bookingRepository.findByCar(car);
                    if (bookings == null || bookings.isEmpty()) {
                        return true; // No bookings, so car is available
                    }

                    return bookings.stream().noneMatch(b -> {
                        // This is the critical null check.
                        if (b == null || b.getStartDate() == null || b.getEndDate() == null) {
                            System.err.println("Skipping invalid booking with null dates for car: " + car.getCarId());
                            return false; // This invalid booking doesn't make the car unavailable
                        }

                        // Direct use of LocalDate without conversion
                        LocalDate bookingStart = b.getStartDate();
                        LocalDate bookingEnd = b.getEndDate();

                        // Check for date overlap
                        return !(bookingEnd.isBefore(startDate) || bookingStart.isAfter(endDate));
                    });
                })
                .collect(Collectors.toList());
    }
}