package ru.orlovs.handbook.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "regions")
public interface RegionRepo extends JpaRepository<Region, Long> {
}
