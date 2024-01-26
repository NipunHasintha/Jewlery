package com.project.studiomanagementsystem.repository;

import com.project.studiomanagementsystem.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Inventoryrepository extends JpaRepository<Inventory,Long> {
}
