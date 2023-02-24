package com.github.receipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceipeRepository extends JpaRepository<ReceipeEntity, String>,
        JpaSpecificationExecutor<ReceipeEntity> {

}

