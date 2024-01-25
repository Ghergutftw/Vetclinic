package app.service;

import app.entity.Adoption;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdoptionService {

    List<Adoption> getAllAdoptions();

    

}
