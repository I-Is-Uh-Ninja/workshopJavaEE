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
    private List<Account> accountsVanKlant;
    private Integer klantId;
    //private List<Klant> klanten;
    private Klant selectedKlant;
    
    
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private KlantFacade klantFacade;
    
    public AccountBean() {
        selectedAccount = new Account();
        selectedKlant = new Klant();
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
    
    public Klant getSelectedKlant () {
        return selectedKlant;
    }
    
    public void setSelectedKlant(Klant selectedKlant) {
        this.selectedKlant = selectedKlant;
    }
    
    public List<Account> getAccountsVanKlant() {
        return accountsVanKlant;
    }
    
    public void setAccountsVanKlant(List<Account> accountsVanKlant) {
        this.accountsVanKlant = accountsVanKlant;
    }
    
   /* public List<Klant> getKlanten() {
        return klanten;
    }

    public void setKlanten(List<Klant> klanten) {
        this.klanten = klanten;
    }*/
    
    //=====Adding and removing from account list=====
    
    public void addToAccounts() {
        //selectedKlant = new Klant();
        selectedKlant.setIdKlant(klantId);
        selectedAccount.setKlantidKlant(selectedKlant);
        selectedAccount.setCreatieDatum(new Date()); //set Datum
        accountFacade.create(selectedAccount);
        accounts.add(selectedAccount);
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
    
    /*
    
    public void addAccountToAccountsVanKlant(Account account) {
        addToAccounts(account);
        setAccountsVanKlant(accountFacade.findAll());
    }*/
    
    public void addThisAccount() {
        //klant = new Klant();
        //klant.setIdKlant(klantId);
        //selectedAccount.setKlantidKlant(klant);
        selectedAccount.setCreatieDatum(new Date()); //set Datum
        addToAccounts();
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
    
    public void findAccounts(Klant klant) {
        setSelectedKlant(klant);
        setAccountsVanKlant(accountFacade.findAccountByKlantId(selectedKlant.getIdKlant()));
    }
    
    
    @PostConstruct
    private void init() {
        setAccounts(accountFacade.findAll());
        //setKlanten(klantFacade.findAll());
    }
}
