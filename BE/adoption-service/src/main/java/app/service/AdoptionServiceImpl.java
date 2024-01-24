package app.service;

import app.entity.Adoption;
import app.repository.AdoptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository adoptionRepository;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
    }

    @Override
    public List<Adoption> getAllAdoptions() {
        log.info("Fetching all adoptions");
        return adoptionRepository.findAll();
    }
}
