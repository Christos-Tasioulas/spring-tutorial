package com.example.jobportal.scopes;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
@Getter @Service
public class ApplicationScopedBean {
    private int visitorsCount;

    public ApplicationScopedBean() {
        System.out.println("ApplicationScopedBean created");
    }

    public void incrementVisitorsCount() {
        visitorsCount++;
    }
}
