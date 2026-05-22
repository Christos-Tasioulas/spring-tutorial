package com.eazybytes.ex4;

import com.eazybytes.ex4.beans.Car;
import com.eazybytes.ex4.beans.Engine;
import com.eazybytes.ex4.beans.Person;
import com.eazybytes.ex4.beans.Vehicle;
import com.eazybytes.ex4.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example4 {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var person = context.getBean(Person.class);
        var vh = context.getBean(Vehicle.class);
        System.out.println("Person name from Spring context: " + person.getName());
        System.out.println("Vehicle name from Spring context is: " + vh.getName());
        System.out.println("Vehicle that person owns is: " + person.getVehicle());

        var car = context.getBean(Car.class);
        var engine = context.getBean(Engine.class);
        System.out.println("Car name from Spring context: " + car.getName());
        System.out.println("Engine name from Spring context is: " + engine.getName());
        System.out.println("Engine that car owns is: " +car.getEngine());
    }
}
