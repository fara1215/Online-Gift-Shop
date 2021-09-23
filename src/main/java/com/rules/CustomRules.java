package com.rules;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;


@Named("rules")
@SessionScoped
public class CustomRules implements Serializable {
	MessageProvider mp = new MessageProvider();
	public void emailRule (String email, String Demail,
			String pass, String Dpass)throws RulesException {
		System.out.println("++++++"+Demail+"++++++"+Dpass+"++++++++"+email+"+++++"+pass);
		if(!(pass.equals(Dpass)&& email.equals(Demail)))
			 throw new RulesException(mp.getValue("emailMismatchError"));
	}

	public void emailNotFoundRule()throws RulesException{
		MessageProvider mp = new MessageProvider();
		
			 throw new RulesException(mp.getValue("emailNotFoundError"));
	
	}
	public void validateQuantity(FacesContext context, UIComponent component, Object value) { 
		System.out.println("+++++++++++++++++Error in quantity");
		Integer valueS=(Integer)(value);
		Integer  zero=0;
		if(valueS.equals(zero)){
			
			MessageProvider mp = new MessageProvider();
			//context.addMessage(component.getClientId(),null);
			FacesMessage errMsg=new FacesMessage(FacesMessage.SEVERITY_ERROR, mp.getValue("quantity"), null);
			throw new ValidatorException(errMsg);
		}
	}
}