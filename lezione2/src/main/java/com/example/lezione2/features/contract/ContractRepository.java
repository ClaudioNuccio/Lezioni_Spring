package com.example.lezione2.features.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {

    @Query(value = "SELECT * FROM contract_entity c WHERE team_id = :teamId", nativeQuery = true)
    List<ContractEntity> getContractsByTeam(Long teamId);
}
