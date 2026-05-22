package com.eazybytes.ex6;

import com.eazybytes.ex6.beans.Bike;
import com.eazybytes.ex6.beans.Engine;
import com.eazybytes.ex6.beans.Vehicle;
import com.eazybytes.ex6.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example6 {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        System.out.println("\n=== AVAILABLE BEANS ===");

        if (context.containsBean("engine")) {
            Engine engine =  context.getBean(Engine.class);
            System.out.println("Engine name =" + engine.getName());
        }
        if (context.containsBean("vehicle")) {
            Vehicle vehicle = context.getBean(Vehicle.class);
            System.out.println("Vehicle name =" + vehicle.getName());
            System.out.println("Vehicle engine =" + vehicle.getEngine());
        }
        if (context.containsBean("bike")) {
            Bike bike =  context.getBean(Bike.class);
            System.out.println("Bike name =" + bike.getName());
        }
    }
}
