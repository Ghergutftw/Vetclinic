package app.service;

import app.dto.AdoptionDTO;
import app.dto.Response;
import app.entity.Adoption;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdoptionService {

    List<Adoption> getAllAdoptions();
    Adoption getAdoptionById(Integer id);
    Response createAdoption(AdoptionDTO adoption);
    Adoption updateAdoption(Integer id, Adoption adoption);
    void deleteAdoption(Integer id);

    

}
