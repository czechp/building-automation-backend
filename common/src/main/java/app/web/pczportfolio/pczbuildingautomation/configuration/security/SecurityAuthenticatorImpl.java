package app.web.pczportfolio.pczbuildingautomation.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SecurityAuthenticatorImpl implements SecurityAuthenticator {
    private final AuthenticationManager authenticationManager;

    @Override
    public void authenticateAccount(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
