package com.users.users.services.interfaces;

import com.users.users.dto.CompaniesDto;
import com.users.users.dto.GasRetailerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface GasRetailerService {

    GasRetailerDto save(GasRetailerDto dto, MultipartFile imageFile) throws IOException;

    GasRetailerDto findById(Integer id);

    List<GasRetailerDto> findAll();

    void delete(Integer id);

    List<GasRetailerDto> getNearbyRetailers(double lat, double lng);
}
