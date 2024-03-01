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

    public ContractEntity addPlayerToTeam(long idPlayer, long idTeam) {
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setPlayerEntity(playerRepository.getReferenceById(idPlayer));
        contractEntity.setTeamEntity(teamRepository.getReferenceById(idTeam));
        contractRepository.saveAndFlush(contractEntity);
        return contractEntity;
    }

    public List<ContractEntity> getAllContracts() {
        return contractRepository.findAll();
    }

    public ContractEntity updateContract(long id, ContractEntity contractEntity) {
        Optional<ContractEntity> contractToUpdate = contractRepository.findById(id);
        if (contractToUpdate.isPresent()) {
            contractToUpdate.get().setPlayerEntity(contractEntity.getPlayerEntity());
            contractToUpdate.get().setTeamEntity(contractEntity.getTeamEntity());
            return contractRepository.saveAndFlush(contractToUpdate.get());
        } else {
            return null;
        }
    }

    public List<ContractEntity> getContractsByTeam(Long teamId){
        return contractRepository.getContractsByTeam(teamId);
    }

}
