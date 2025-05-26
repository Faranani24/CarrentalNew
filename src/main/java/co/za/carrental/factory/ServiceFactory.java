package co.za.carrental.factory;

import co.za.carrental.domain.CarService;

public class ServiceFactory {
    public static CarService createService(String serviceId, String name, float costPerDay){
        return new CarService.Builder()
                .setServiceId(serviceId)
                .setName(name)
                .setCostPerDay(costPerDay)
                .build();
    }
}
/*
ServiceFactory.java
CarService Factory
Thabiso Kama
18 May 2025
*/