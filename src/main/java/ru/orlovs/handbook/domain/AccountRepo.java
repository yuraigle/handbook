package ru.orlovs.handbook.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String email);
}
