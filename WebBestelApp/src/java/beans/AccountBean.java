/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Account;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import session.AccountFacade;
/**
 *
 * @author Wytze Terpstra
 */
@Named
@Dependent
@Stateless
public class AccountBean implements Serializable{
    String title;
    Account selectedAccount;
    List<Account> accounts;
    @EJB
    AccountFacade accountFacade;
    
    public AccountBean() {
        selectedAccount = new Account();
        title = "JSF managed AccountBean";
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }
    
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    public void addToAccounts(Account account) {
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
        addToAccounts(selectedAccount);
        selectedAccount = new Account();
    }
    
    public String editThisAccount() {
        accountFacade.edit(selectedAccount);
        selectedAccount = new Account();
        return "accountIndex";
    }
    
    public String goToEditAccount (Account account){
        setSelectedAccount(account);
        return "editAccount";
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Account getSelectedAccount() {
        return selectedAccount;
    }
    
    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }
    
    @PostConstruct
    private void inti() {
        setAccounts(accountFacade.findAll());
    }
}
