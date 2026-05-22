package com.eazybytes.ex3;

import com.eazybytes.ex3.beans.Vehicle;
import com.eazybytes.ex3.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example3 {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var vh = context.getBean(Vehicle.class);
        System.out.println("Vehicle name from Spring context is: " + vh.getName());
        vh.sayHello();
        context.close();
    }
}
