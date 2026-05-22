package com.eazybytes.ex7;

import com.eazybytes.ex7.beans.MyService;
import com.eazybytes.ex7.beans.UserSession;
import com.eazybytes.ex7.config.ProjectScopeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Example7 {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectScopeConfig.class);
        var myService1 = context.getBean(MyService.class);
        var myService2 = context.getBean(MyService.class);
        System.out.println(myService1.hashCode());
        System.out.println(myService2.hashCode());

        var user1 = context.getBean(UserSession.class);
        var user2 = context.getBean(UserSession.class);
        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());
        System.out.println(user1.getSessionId());
        System.out.println(user2.getSessionId());
    }
}
