package com.bewatches.server.Repository;

import com.bewatches.server.Model.Parent.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<Audio, Long> {
}
