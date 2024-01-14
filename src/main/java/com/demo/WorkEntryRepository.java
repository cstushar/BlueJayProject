package com.demo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is a Spring Data JPA repository interface for the WorkEntry entity.<br>
 * It provides basic CRUD operations for WorkEntry objects and can be extended to add custom queries.<br>
 * The first type parameter (WorkEntry) specifies the entity type, and the second type parameter (Long) specifies the ID type.
 */
public interface WorkEntryRepository extends JpaRepository<WorkEntry, Long> {
    // Add custom queries if needed
}
