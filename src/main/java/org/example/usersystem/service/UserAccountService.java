package org.example.usersystem.service;

import org.example.usersystem.model.entity.UserAccount;
import org.example.usersystem.model.repository.UserAccountRepository;

public class UserAccountService {
    private final UserAccountRepository accountRepository = new UserAccountRepository();

    public void createUser(String username, String password) {
        accountRepository.createUser(new UserAccount(username, password));
    }

    public boolean login(String username, String password) {
        try {
            UserAccount account = accountRepository.findByUsername(username);
            return account.password().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}