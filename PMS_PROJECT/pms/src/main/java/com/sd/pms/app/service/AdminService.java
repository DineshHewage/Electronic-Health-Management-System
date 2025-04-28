package com.sd.pms.app.service;

import com.sd.pms.app.entity.Admin;
import com.sd.pms.app.exceptions.HttpErrorException;
import com.sd.pms.app.dto.CreateAdminDto;
import com.sd.pms.app.dto.UpdateAdminDto;

import java.util.List;

public interface AdminService {
    Admin createAdmin(CreateAdminDto dto) throws HttpErrorException;
    Admin updateAdmin(Long adminId , UpdateAdminDto dto) throws HttpErrorException;
    void deleteAdmin(Long adminId) throws HttpErrorException;
    List<Admin> getAllAdmins();
    Admin getAdmin(Long id) throws HttpErrorException;
}
