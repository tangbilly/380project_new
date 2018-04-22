package project.project_new.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project_new.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
