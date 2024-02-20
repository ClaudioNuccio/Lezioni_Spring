package com.example.lezione2.features.contract;

import com.example.lezione2.features.player.PlayerRepository;
import com.example.lezione2.features.team.TeamRepository;
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
        contract.setPlayerEntity(playerRepository.getReferenceById(idPlayer));
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
            contractToUpdate.get().setPlayerEntity(contract.getPlayerEntity());
            contractToUpdate.get().setTeam(contract.getTeam());
            return contractRepository.saveAndFlush(contractToUpdate.get());
        } else {
            return null;
        }
    }

}
