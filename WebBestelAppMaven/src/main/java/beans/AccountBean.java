/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Account;
import entity.Klant;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import session.AccountFacade;
import session.KlantFacade;
/**
 *
 * @author Wytze Terpstra
 */
@Named
@Dependent
@Stateless
public class AccountBean implements Serializable{
    
    private Account selectedAccount;
    private List<Account> accounts;
    private Integer klantId;
    private List<Klant> klanten;
    private Klant klant;
    
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private KlantFacade klantFacade;
    
    public AccountBean() {
        selectedAccount = new Account();
        //selectedAccount.setCreatieDatum(new Date());
    }
    
    //=====Getters and Setters=========
    
    public List<Account> getAccounts() {
        return accounts;
    }
    
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
       
    public Account getSelectedAccount() {
        return selectedAccount;
    }
    
    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    public Integer getKlantId(){
        return klantId;
    }
    
    public void setKlantId(Integer id){
        klantId = id;
    }
    
    public List<Klant> getKlanten() {
        return klanten;
    }

    public void setKlanten(List<Klant> klanten) {
        this.klanten = klanten;
    }
    
    //=====Adding and removing from account list=====
    
    public void addToAccounts(Account account) {
        account.setCreatieDatum(new Date()); //set Datum
        klant = new Klant();
        klant.setIdKlant(klantId);
        account.setKlantidKlant(klant);
        accountFacade.create(account);
        accounts.add(account);
    }
    
    public void removeFromAccounts(Account account) {
        int accountsListSize = accounts.size();
        int accountIndex = -1;
        for (int i = 0; i < accountsListSize; i++) {
            if (accounts.get(i) == account) {
                accountIndex = i;
            }
        }
        if (accountIndex != -1) {
            accountFacade.remove(accounts.get(accountIndex));
            accounts.remove(accountIndex);
        }
    }
    
    public void addThisAccount() {
        klant = new Klant();
        klant.setIdKlant(klantId);
        selectedAccount.setKlantidKlant(klant);
        selectedAccount.setCreatieDatum(new Date()); //set Datum
        addToAccounts(selectedAccount);
        selectedAccount = new Account();
    }
    
     //=====Edit account=====
    
    public String editThisAccount() {
        accountFacade.edit(selectedAccount);
        selectedAccount = new Account();
        return "accountlijst";
    }
    
    public String goToEditAccount (Account account){
        setSelectedAccount(account);
        return "editAccount";
    }

    //=====Other=====
    
    @PostConstruct
    private void init() {
        setAccounts(accountFacade.findAll());
        setKlanten(klantFacade.findAll());
    }
}
