package BuggyCoder01.DistributedSystem.common.repository;

import BuggyCoder01.DistributedSystem.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}