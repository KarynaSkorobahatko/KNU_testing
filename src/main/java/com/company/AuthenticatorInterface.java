package com.company;

import java.io.File;

public interface AuthenticatorInterface {

    boolean authenticateUser(String username, String password) throws EmptyCredentialsException;

    void login();
}
