package co.za.carrental.factory;

import co.za.carrental.domain.Service;

public class ServiceFactory {
    public static Service createService(String serviceId, String name, float costPerDay){
        return new Service.Builder()
                .setServiceId(serviceId)
                .setName(name)
                .setCostPerDay(costPerDay)
                .build();
    }
}
/*
ServiceFactory.java  
Service Factory  
Thabiso Kama  
18 May 2025  
*/
