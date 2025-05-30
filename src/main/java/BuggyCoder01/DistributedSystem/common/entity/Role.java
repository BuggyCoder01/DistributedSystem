package BuggyCoder01.DistributedSystem.common.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; //  "ROLE_MANAGER" or "ROLE_EMPLOYEE"

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions; //  ["FILE_READ", "FILE_WRITE"]

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}