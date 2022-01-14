
package com.barber.shop.model.dto;

import com.barber.shop.annotations.ValidPassword;
import java.io.Serializable;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
    
    @Email
    private String email;
    
    @ValidPassword
    private String password;
    
    @ValidPassword
    private String confirmPassword;
    
}
