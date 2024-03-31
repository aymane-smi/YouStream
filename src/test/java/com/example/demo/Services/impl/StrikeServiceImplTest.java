package com.example.demo.Services.impl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import com.example.demo.Models.DTO.Strike.StrikeDTO;
import com.example.demo.Models.Entites.Strike;
import com.example.demo.Repositories.StrikeRepository;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StrikeServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private StrikeRepository strikeRepository;
    @InjectMocks
    private StrikeServiceImpl strikeService;

    @Before
    private void init(){
        strikeService = new StrikeServiceImpl(strikeRepository, modelMapper);
    }
    @Test
    @DisplayName("get all reports")
    void testGetAllStrikes() {
        List<Strike> list = new ArrayList<>();
        StrikeDTO[] dto = {};
        when(strikeRepository.findAll()).thenReturn(list);
        when(modelMapper.map(list, StrikeDTO[].class)).thenReturn(dto);
        assertEquals(strikeService.getAllStrikes().size(), 0);
    }
}
