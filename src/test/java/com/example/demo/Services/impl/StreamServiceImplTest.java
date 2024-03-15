package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.demo.Models.DTO.Stream.StreamTopDTO;
import com.example.demo.Models.Entites.Stream;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Services.StreamService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StreamServiceImplTest {

    @Mock
    private StreamRepository streamRepository;
    @Mock
    private ModelMapper modelMapper;
    @MockBean
    private StreamService streamService;

    @BeforeEach
    public void init(){
        streamService = new StreamServiceImpl(modelMapper, streamRepository);
    }

    @Test
    @DisplayName("check the top streams")
    void testGetTopTenStream() {
        List<Stream> list = new ArrayList<>();
        StreamTopDTO[] listDTO = {};
        when(streamRepository.findFirst10ByOrderByViewsNbrDesc()).thenReturn(list);
        when(modelMapper.map(list, StreamTopDTO[].class)).thenReturn(listDTO);
        assertEquals(streamService.getTopTenStream().size(), 0);
    }

    @Test
    @DisplayName("get stream using pagination")
    void testGetAllStream(){
        PageRequest page = PageRequest.of(0, 10);
        List<Stream> list = new ArrayList<>();
        Page<Stream> result = new PageImpl<>(list);
        when(streamRepository.findByRestrictedIs(true, page)).thenReturn(result);
        assertEquals(streamService.getAllStream(0).size(), 0);
    }
}
