package com.eazybytes.ex1;

import com.eazybytes.ex1.beans.Vehicle;
import com.eazybytes.ex1.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example1 {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var vh = context.getBean("vehicle1", Vehicle.class);
        System.out.println("Vehicle name from Spring context is: " + vh.getName());

        var vehicle = (Vehicle) context.getBean("vehicle2");
        System.out.println("Vehicle name from Spring context is: " + vehicle.getName());
    }
}
