package com.bewatches.server.Repository;

import com.bewatches.server.Model.Parent.Warning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningRepository extends JpaRepository<Warning, Long> {
}
