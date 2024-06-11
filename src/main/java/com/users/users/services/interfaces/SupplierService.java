package com.users.users.services.interfaces;


import com.users.users.dto.SupplierDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface SupplierService {

    SupplierDto save(SupplierDto dto, MultipartFile imageFile) throws IOException;

    SupplierDto findById(Integer id);

    List<SupplierDto> findAll();

    void delete(Integer id);


}

