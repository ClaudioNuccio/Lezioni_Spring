package com.example.lezione2.services;

import com.example.lezione2.entities.Contract;
import com.example.lezione2.repositories.ContractRepository;
import com.example.lezione2.repositories.PlayerRepository;
import com.example.lezione2.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    public Contract addPlayerToTeam(long idPlayer, long idTeam) {
        Contract contract = new Contract();
        contract.setPlayer(playerRepository.getReferenceById(idPlayer));
        contract.setTeam(teamRepository.getReferenceById(idTeam));
        contractRepository.saveAndFlush(contract);
        return contract;
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract updateContract(long id, Contract contract) {
        Optional<Contract> contractToUpdate = contractRepository.findById(id);
        if (contractToUpdate.isPresent()) {
            contractToUpdate.get().setPlayer(contract.getPlayer());
            contractToUpdate.get().setTeam(contract.getTeam());
            return contractRepository.saveAndFlush(contractToUpdate.get());
        } else {
            return null;
        }
    }

}
