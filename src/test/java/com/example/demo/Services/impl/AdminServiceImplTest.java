package com.example.demo.Services.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Configurations.Security.JwtService;
import com.example.demo.Models.DTO.Admin.AdminDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.Entites.Admin;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.AdminRefreshRepository;
import com.example.demo.Repositories.AdminRepository;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private AdminServiceImpl adminServiceImpl;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AdminDetailsImpl adminDetailsImpl;
    @Mock
    private JwtService jwtService;
    @Mock
    private AdminRefreshRepository adminRefreshRepository;
    private Admin admin;
    private AdminDTO adminDTO;

    private AdminRDTO adminRDto;
    @BeforeEach
    public void setup(){
        admin = new Admin();
        admin.setId(1);
        admin.setUsername("admin");
        admin.setPassword(
            "admin"
        );
        admin.setRole(Role.ADMIN);
        System.out.println("wast setup");
        adminServiceImpl = new AdminServiceImpl(passwordEncoder, adminRepository, adminDetailsImpl, jwtService, modelMapper, adminRefreshRepository);
        adminRDto = new AdminRDTO();
        adminRDto.setId(1);
        adminRDto.setUsername("admin");
        adminRDto.setPassword(
            "admin"
        );
        adminDTO = new AdminDTO();
        adminDTO.setId(1);
        adminDTO.setUsername("admin");
        adminDTO.setPassword(
            "admin"
        );
        

    }
    // @Test
    // @DisplayName("testing the creation of a refresh token")
    // void testCreateRefreshToken() {

    // }

    // @Test
    // @DisplayName("testing login of a admin")
    // void testLogin() {

    // }

    // @Test
    // @DisplayName("testing logout of admin by giving the token")
    // void testLogout() {

    // }

    @Test
    @DisplayName("testing signup functionality of admin")
    void testSignup() {
        when(adminRepository.save(admin)).thenReturn(admin);
        when(modelMapper.map(adminRDto, Admin.class)).thenReturn(admin);
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);
        assertSame( adminDTO, adminServiceImpl.signup(adminRDto));
    }
}
