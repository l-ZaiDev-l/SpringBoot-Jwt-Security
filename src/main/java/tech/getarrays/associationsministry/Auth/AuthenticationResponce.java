package tech.getarrays.associationsministry.Auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponce {
    private String accessToken;
}
