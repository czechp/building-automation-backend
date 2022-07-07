package app.web.pczportfolio.pczbuildingautomation.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SecurityCurrentUserImpl implements SecurityCurrentUser {

    @Override
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
