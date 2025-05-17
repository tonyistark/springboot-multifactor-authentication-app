# SpringBoot App with Multifactor Authentication
<p>
This is a Simple Spring boot app which incorporates 2 factor authentication.
</p>
<p>
This app uses TOTP Algorithm to perform the 2nd Factor Authentication. Any TOTP Mobile App can be used to perform the authentication like Google Authenticator, Microsoft Authenticator,etc.
</p>
<p>
The implementation includes a lightweight `FastTotp` utility for generating and verifying codes with minimal overhead.
</p>
<h3> Below is the simple design of the components used: </h3>
<p align="center">
  <img src="docs/MFA_App_Design_1.svg" />
</p>


## Running locally

Ensure Java 17 is installed. Build and run the application using Maven:

```bash
mvn spring-boot:run
```

## Running tests

Execute unit tests with:

```bash
mvn test
```
