package com.users.users.services.interfaces;


import com.users.users.dto.CompaniesDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CompaniesService {

    CompaniesDto save(CompaniesDto dto, MultipartFile imageFile) throws IOException;

    CompaniesDto findById(Integer id);

    List<CompaniesDto> findAll();

    void delete(Integer id);

}
