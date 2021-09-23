package com.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dao.AbstractFacade;
import com.dao.AccountDAO;
import com.dao.HibernateUtil;
import com.dao.IAccountDAO;
import com.entity.Account;
import com.entity.Product;
import com.rules.CustomRules;
import com.rules.MessagesUtil;
import com.rules.RulesException;

@Named("accountCont")
@SessionScoped
public class AccountContrl implements Serializable {
	AbstractFacade acDao;
	private Account account;
	private boolean edited;
	private UIComponent loginBtn;
	private boolean loggedIn = false;

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	public UIComponent getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(UIComponent loginBtn) {
		this.loginBtn = loginBtn;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Account getAccount() {
		return account;
	}

	public Account getSelected() {
		if (account == null) {
			account = new Account();
		}
		System.out.println("get selected" + account.getFirstName());
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	private static SessionFactory sf = HibernateUtil.getSessionFactory();

	public AccountContrl() {
		acDao = new AccountDAO();
	}

	public String createAccount() {
		Transaction tx = sf.getCurrentSession().beginTransaction();

		System.out.println("create account" + account.getFirstName());
		acDao.saveEntity(account);

		tx.commit();
		loggedIn = true;
		return "selectedProducts";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		loggedIn = false;
		return "index";
	}

	public String doLogin() {
		Transaction tx = sf.getCurrentSession().beginTransaction();
		AccountDAO dao = new AccountDAO();
		String pass = account.getPassword();
				/////mahder/////////////////////
		/*account = dao.getAccountByEmail(account.getEmail());

		 if (account == null) {// we can do rule for the error later
			return "login.xhtml";
		}

		if (pass.equals(account.getPassword())) {
			loggedIn = true;
			return "selectedProducts";
		}///////////////mahder*/
		String email = account.getEmail();
		
		try {
			CustomRules c = new CustomRules();			
			account = dao.getAccountByEmail(account.getEmail());		
			c.emailRule(email, account.getEmail(), pass, account.getPassword());
			loggedIn = true;
			return "selectedProducts";
		} catch(NullPointerException e) {
			try {
				CustomRules c = new CustomRules();
				c.emailNotFoundRule();
				
			} catch (RulesException ex) {
				MessagesUtil.displayError(loginBtn, ex.getMessage());
			}
						
		}catch(RulesException e){
			MessagesUtil.displayError(loginBtn, e.getMessage());
		}
		tx.commit();
		
		
		return "login.xhtml";

	}

	@SuppressWarnings("unchecked")
	public String editProfile() {

		Transaction tx = sf.getCurrentSession().beginTransaction();
		acDao.updateEntity(account);
		edited = false;
		tx.commit();
		return null;
	}

	public String readyEditProfile() {
		edited = true;
		return null;
	}

}
