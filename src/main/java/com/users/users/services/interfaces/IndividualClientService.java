package com.users.users.services.interfaces;


import com.users.users.dto.IndividualClientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface IndividualClientService {


    IndividualClientDto save(IndividualClientDto dto, MultipartFile imageFile) throws IOException;

    IndividualClientDto findById(Integer id);

    List<IndividualClientDto> findAll();

    void delete(Integer id);

//    List<LigneCommandeIndividualClientDto> findHistoriqueCommandeIndividualClient(Integer idArticle);

}
