package by.project.trucking_v2.service;

import by.project.trucking_v2.exception.NotFoundException;
import by.project.trucking_v2.model.LegalEntity;
import by.project.trucking_v2.repository.LegalEntityRepository;
import by.project.trucking_v2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalEntityServiceImpl implements LegalEntityService {
    @Autowired
    LegalEntityRepository legalEntityRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<LegalEntity> getAll() {
        Iterable<LegalEntity> iterable = legalEntityRepository.findAll();
        List<LegalEntity> legalEntities = Streamable.of(iterable).toList();
        return legalEntities;
    }

    @Override
    public LegalEntity findById(int id) {
        return legalEntityRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }


    @Override
    public LegalEntity update(int id, LegalEntity legalEntity) {
        LegalEntity currentLegalEntity = legalEntityRepository.findById(id).orElseThrow();
        currentLegalEntity.setTitle(legalEntity.getTitle());
        currentLegalEntity.setContact(legalEntity.getContact());
        return legalEntityRepository.save(currentLegalEntity);
    }

}