/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Login;
import helpers.SCryptHelper;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import session.LoginFacade;

/**
 *
 * @author Ian
 */
@Named
@Dependent
@Stateless
public class LoginBean {
    
    Login login;
    
    @EJB
    LoginFacade loginFacade;
    
    public LoginBean(){
        login = new Login();
    }
    
    
    //=====Getters and setters=====

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    //=====Logging in and out======

    //validate login
    public String validateUsernamePassword() {
        boolean valid = false;
        List<Login> foundLogins = loginFacade.findByInlognaam(login.getInlognaam());
        for(Login l : foundLogins){
            if(SCryptHelper.validate(login.getInlogwachtwoord(), l.getInlogwachtwoord())){
                valid = true;
                login = l;
                break;
            }
        }
        if (valid) {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("username", login.getInlognaam());
            login = new Login();
            return "keuzemenu";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }
 
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        return "login";
    }
    
    //=====Add login=====
    public void addLogin(){
        login.setInlogwachtwoord(SCryptHelper.encrypt(login.getInlogwachtwoord()));
        loginFacade.create(login);
        login = new Login();
    }
}
