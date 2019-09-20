package com.bewatches.server.Repository;

import com.bewatches.server.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
