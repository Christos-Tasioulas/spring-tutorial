package com.eazybytes.ex2;

import com.eazybytes.ex2.beans.Vehicle;
import com.eazybytes.ex2.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example2 {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var vh = context.getBean("audiVehicle", Vehicle.class);
        System.out.println("Vehicle name from Spring context is: " + vh.getName());

        var vehicle = (Vehicle) context.getBean("myFavouriteVehicle");
        System.out.println("Vehicle name from Spring context is: " + vehicle.getName());

        var vhcle = context.getBean(Vehicle.class);
        System.out.println("Vehicle name from Spring context is: " + vhcle.getName());

        var helloWorld = context.getBean(String.class);
        System.out.println("String value from Spring context is: " + helloWorld);
    }
}
