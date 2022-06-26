package app.web.pczportfolio.pczbuildingautomation.account.application.dto;

import app.web.pczportfolio.pczbuildingautomation.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

public interface  AccountQueryDto {
     long getId();
     String getUsername();
     String getEmail();

     @Value("#{target.accountRole}")
     String getRole();

}
