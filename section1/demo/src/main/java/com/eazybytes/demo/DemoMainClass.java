package com.eazybytes.demo;

import com.eazybytes.demo.beans.Vehicle;
import com.eazybytes.demo.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoMainClass {
    static void main() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Audi");
        System.out.println("Vehicle name from non-spring context is: " + vehicle.getName());

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var vh = context.getBean(Vehicle.class);
        System.out.println("Vehicle name from Spring context is: " + vh.getName());

        String hello = context.getBean(String.class);
        System.out.println("String value from Spring context is: " + hello);

        Integer num =  context.getBean(Integer.class);
        System.out.println("Integer value from Spring context is: " + num);

        String hello1 = (String) context.getBean("hello");
        System.out.println("String value from Spring context is: " + hello1);

//        Integer num1 =   context.getBean(Integer.class);
//        System.out.println("Integer value from Spring context is: " + num1);
    }
}
