package BuggyCoder01.DistributedSystem.common.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private Set<String> roles;
    private Set<String> departments;
}