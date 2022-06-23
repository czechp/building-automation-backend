package app.web.pczportfolio.pczbuildingautomation.account.adapter.persistence;

import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityUserDetailsService;
import app.web.pczportfolio.pczbuildingautomation.configuration.security.SecurityCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AccountUserDetailsServiceProvider implements  SecurityUserDetailsService {
    private final AccountJpaRepository accountJpaRepository;
    private final SecurityCurrentUser securityCurrentUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

}
