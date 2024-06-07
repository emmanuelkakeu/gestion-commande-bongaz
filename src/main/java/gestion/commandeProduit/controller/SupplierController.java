package gestion.commandeProduit.controller;

import java.util.List;

import gestion.commandeProduit.DTO.SupplierDto;
import gestion.commandeProduit.controller.api.SupplierApi;
import gestion.commandeProduit.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public SupplierDto save(SupplierDto dto) {
        return supplierService.save(dto);
    }

    @Override
    public SupplierDto findById(Integer id) {
        return supplierService.findById(id);
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierService.findAll();
    }

    @Override
    public void delete(Integer id) {
        supplierService.delete(id);
    }
}
