package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Configurations.Security.JwtService;
import com.example.demo.Models.DTO.Admin.AdminDTO;
import com.example.demo.Models.DTO.Admin.AdminLoginDTO;
import com.example.demo.Models.DTO.Admin.AdminRDTO;
import com.example.demo.Models.DTO.Admin.SignedAdminDTO;
import com.example.demo.Models.Entites.Admin;
import com.example.demo.Models.Entites.AdminRefreshToken;
import com.example.demo.Models.Enums.Role;
import com.example.demo.Repositories.AdminRefreshRepository;
import com.example.demo.Repositories.AdminRepository;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdminServiceImplTest {
    @Mock
    private AdminRepository adminRepository;
    @MockBean
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
    private SignedAdminDTO signedAdminDTO;
    private AdminRefreshToken adminRefreshToken;
    private AdminRDTO adminRDto;
    private AdminLoginDTO adminLoginDTO;
    @BeforeEach
    public void setup(){
        //seting admin
        admin = new Admin();
        admin.setId(1);
        admin.setUsername("admin");
        admin.setPassword(
            "admin"
        );
        admin.setRole(Role.ADMIN);
        adminServiceImpl = new AdminServiceImpl(passwordEncoder, adminRepository, adminDetailsImpl, jwtService, modelMapper, adminRefreshRepository);
        //setting admin register dto
        adminRDto = new AdminRDTO();
        adminRDto.setId(1);
        adminRDto.setUsername("admin");
        adminRDto.setPassword(
            "admin"
        );
        //setting admin dto
        adminDTO = new AdminDTO();
        adminDTO.setId(1);
        adminDTO.setUsername("admin");
        adminDTO.setPassword(
            "admin"
        );
        //setting signed admin dto
        signedAdminDTO = new SignedAdminDTO();
        signedAdminDTO.setId(1);
        signedAdminDTO.setUsername("admin");
        signedAdminDTO.setToken("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        signedAdminDTO.setRefresh_token("TEST");

        //setting admin refresh token entity
        adminRefreshToken = new AdminRefreshToken();
        adminRefreshToken.setAdmin(admin);
        adminRefreshToken.setId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        //setting admin login dto 
        adminLoginDTO = new AdminLoginDTO();
        adminLoginDTO.setUsername("admin");
        adminLoginDTO.setPassword("admin");
        //making refresh entity
        adminRefreshToken = new AdminRefreshToken();
        adminRefreshToken.setAdmin(admin);
        adminRefreshToken.setId(UUID.randomUUID());

    }

    @Test
    @DisplayName("testing the creation of a refresh token")
    void testCreateRefreshToken() {
        var userDetail = new User(
            admin.getUsername(),
            admin.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.name()))
        );
        when(adminRefreshRepository.findById(adminRefreshToken.getId())).thenReturn(Optional.of(adminRefreshToken));
        when(adminDetailsImpl.loadUserByUsername(admin.getUsername())).thenReturn(userDetail);
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(adminRepository.findIdByUsername("admin")).thenReturn(Optional.of(1L));
        SignedAdminDTO tmp = adminServiceImpl.createRefreshToken(adminRefreshToken.getId());
        signedAdminDTO.setToken(
            jwtService.generateToken(userDetail)
        );
        assertEquals(tmp.getToken(), signedAdminDTO.getToken());
    }

    // @Test
    // @DisplayName("testing login of a admin")
    // void testLogin() {
    //     var userDetail = new User(
    //         admin.getUsername(),
    //         admin.getPassword(),
    //         Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.name()))
    //     );
    //     when(adminDetailsImpl.loadUserByUsername(admin.getUsername())).thenReturn(userDetail);
    //     when(adminRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
    //     when(adminRepository.findIdByUsername("admin")).thenReturn(Optional.of(1L));
    //     when(passwordEncoder.matches("admin", userDetail.getPassword())).thenReturn(true);
    //     adminRefreshToken.setAdmin(admin);
    //     adminRefreshToken.setId(UUID.randomUUID());
    //     var refresh = adminRefreshToken;
    //     when(adminRefreshRepository.save(adminRefreshToken)).thenReturn(refresh);
    //     SignedAdminDTO tmp = adminServiceImpl.login(adminLoginDTO);
    //     signedAdminDTO.setToken(
    //         "test"
    //     );
    //     assertEquals(tmp.getToken(), signedAdminDTO.getToken());
    // }

    @Test
    @DisplayName("testing logout of admin by giving the token")
    void testLogout() {

    }

    @Test
    @DisplayName("testing signup functionality of admin")
    void testSignup() {
        when(adminRepository.save(admin)).thenReturn(admin);
        when(modelMapper.map(adminRDto, Admin.class)).thenReturn(admin);
        when(modelMapper.map(admin, AdminDTO.class)).thenReturn(adminDTO);
        assertSame( adminDTO, adminServiceImpl.signup(adminRDto));
    }
}
