package com.company;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticatorApplicationTest {

    private static final String USERNAME = "karina";
    private static final String PASSWORD = "@karina20";

    @Mock
    private AuthenticatorInterface authenticatorMock;

    @Spy
    List<String> spyList = new ArrayList<String>();

    @InjectMocks
    private AuthenticatorApplication authenticator;

    @Before
    public void setUp() {
        //authenticatorMock = Mockito.mock(AuthenticatorInterface.class);
        authenticator = new AuthenticatorApplication(authenticatorMock);
        System.out.println("Before");
    }

    @Test
    public void testReturn() throws EmptyCredentialsException {
        when(this.authenticatorMock.authenticateUser(USERNAME, PASSWORD)).thenReturn(true);

        boolean resutl_false = authenticator.authenticate("user", "123");
        boolean resutl_true = authenticator.authenticate(USERNAME, PASSWORD);

        assertFalse(resutl_false);
        assertTrue(resutl_true);
        System.out.println("Return Test");
    }

    @Test(expected = NullPointerException.class)
    public void whenConfigNonVoidRetunMethodToThrowEx_thenExIsThrown() throws EmptyCredentialsException {
        when(this.authenticatorMock.authenticateUser(anyString(), anyString()))
                .thenThrow(new NullPointerException("Error occurred"));

        authenticatorMock.authenticateUser("kari", "kari20");
        System.out.println("NullPointerException Test");
    }

    @Test(expected = IllegalStateException.class)
    public void whenConfigVoidRetunMethodToThrowExWithNewExObj_thenExIsThrown() throws EmptyCredentialsException {
        doThrow(new IllegalStateException("Error occurred"))
                .when(authenticatorMock)
                .authenticateUser(anyString(), anyString());

        authenticatorMock.authenticateUser("word", "meaning");
        System.out.println("Illegal State Exception Test");
    }

    @Test
    public void whenUsingTheSpyAnnotation() {
        spyList.add("login");
        spyList.add("password");

        Mockito.verify(spyList).add("login");
        Mockito.verify(spyList).add("password");

        assertEquals(2, spyList.size());

        System.out.println("Test SPY");
        Mockito.doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
        System.out.println("Test Stubbing SPY");
    }

    @Test
    public void testCallAndParemeters() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        verify(authenticatorMock).authenticateUser(USERNAME, PASSWORD);
        //fail verify(authenticatorMock).authenticateUser(USERNAME, "error_password");
        System.out.println("Parameters Test");
    }


    @Test
    public void testTimesCall() throws EmptyCredentialsException {

        authenticator.authenticate(USERNAME, PASSWORD);
        //один раз
        verify(authenticatorMock, times(1)).authenticateUser(USERNAME, PASSWORD);
        //Як мінімум один раз
        verify(authenticatorMock, atLeastOnce()).authenticateUser(USERNAME, PASSWORD);
        //Як мінімум х разів
        verify(authenticatorMock, atLeast(1)).authenticateUser(USERNAME, PASSWORD);
        //Бфльше ніж х раз
        verify(authenticatorMock, atMost(1)).authenticateUser(USERNAME, PASSWORD);
        //Ніколи
        //fail verify(authenticatorMock, never()).authenticateUser(USERNAME, PASSWORD);
        System.out.println("Times Call Test");
    }

    @Test
    public void testCallOrder() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        InOrder inOrder = inOrder(authenticatorMock);
        inOrder.verify(authenticatorMock).login();
        inOrder.verify(authenticatorMock).authenticateUser(USERNAME, PASSWORD);
        System.out.println("Call Order Test");
    }

    @Test
    public void testTimeout() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        verify(authenticatorMock, timeout(1)).authenticateUser(USERNAME, PASSWORD);
        verify(authenticatorMock, timeout(1).times(1)).authenticateUser(USERNAME, PASSWORD);
        System.out.println("Timeout Test");
    }

    @Test(expected = EmptyCredentialsException.class)
    public void testException() throws EmptyCredentialsException {
        System.out.println("Exception");
        when(authenticatorMock.authenticateUser("", "")).thenThrow(new EmptyCredentialsException());
        authenticator.authenticate("", "");
    }

}
