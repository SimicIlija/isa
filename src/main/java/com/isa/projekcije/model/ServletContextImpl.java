package com.isa.projekcije.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component
public class ServletContextImpl implements ServletContextAware {

    private ServletContext servletContext;


    public void setServletContext(Long userId) {
        this.servletContext = servletContext;
        servletContext.setAttribute("loggedInUser", userId);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
