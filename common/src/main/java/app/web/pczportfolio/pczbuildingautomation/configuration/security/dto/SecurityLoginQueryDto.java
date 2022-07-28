package app.web.pczportfolio.pczbuildingautomation.configuration.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SecurityLoginQueryDto {
    private long id;
    private String username;
    private String email;
    private String role;
}
