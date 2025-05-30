package BuggyCoder01.DistributedSystem.common.dto.response;

import lombok.Data;
import java.util.Set;

@Data
public class UserInfoResponse {
    private String username;
    private Set<String> roles;
    private Set<String> departments;
}