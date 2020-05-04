package com.company;


public class AuthenticatorApplication {

    private AuthenticatorInterface authenticator;

    public AuthenticatorApplication(AuthenticatorInterface authenticator) {
        this.authenticator = authenticator;
    }

    public boolean authenticate(String username, String password) throws EmptyCredentialsException {
        this.authenticator.login();
       // System.out.println("Good");
        return this.authenticator.authenticateUser(username, password);
    }


}
