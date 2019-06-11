package com.bewatches.server.Repository;

import com.bewatches.server.Model.Parent.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {
}
