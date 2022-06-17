package app.web.pczportfolio.pczbuildingautomation.configuration.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
class SecurityUtilitiesImpl implements SecurityUtilities {
    @Override
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
