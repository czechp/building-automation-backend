package app.web.pczportfolio.pczbuildingautomation.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class SecurityCurrentUserImpl implements SecurityCurrentUser {
    private final String EMPTY_STRING = "";

    @Override
    public String getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(EMPTY_STRING);
    }


}
