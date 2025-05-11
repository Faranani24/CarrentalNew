/*Service.java
Service Entity Class
Author: Thabiso Kama
Date: 11 May 2025
 */

package co.za.carrental.domain;

public class Service {
    private String serviceId;
    private String name;
    private float costPerDay;

    private Service (Builder builder){
        this.serviceId = builder.serviceId;
        this.name = builder.name;
        this.costPerDay = builder.costPerDay;
    }
    public static class Builder{
        private String serviceId;
        private String name;
        private float costPerDay;

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }
        public void setName(String name) {
            this.name = name;
        }

        public void setCostPerDay(float costPerDay) {
            this.costPerDay = costPerDay;
        }
        public Service build(){
            return new Service(this);
        }
        @Override
        public String toString(){
            return name + "(R "+ costPerDay + " per day)";
        }
    }
}

