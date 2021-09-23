/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rules;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrator
 */
public class MessagesUtil {
    public static void displaySuccess(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
        context.addMessage(null, msg);
    }
    
    /*public static void displayError(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        context.addMessage(null, errMsg);    	
        
    }*/
    public static void displayError(UIComponent component, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage errMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        context.addMessage(component.getClientId(context), errMsg);    	
        
    }
}
