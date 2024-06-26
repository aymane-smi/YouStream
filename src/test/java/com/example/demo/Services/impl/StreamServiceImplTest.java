package com.example.demo.Services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.demo.Models.DTO.Stream.StreamDTO;
import com.example.demo.Models.DTO.Stream.StreamTopDTO;
import com.example.demo.Models.Entites.Stream;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Repositories.TagRepository;
import com.example.demo.Services.StreamService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StreamServiceImplTest {

    @Mock
    private StreamRepository streamRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TagRepository tagRepository;
    @MockBean
    private StreamService streamService;
    private Stream stream;
    private StreamDTO streamDTO;

    @BeforeEach
    public void init(){
        streamService = new StreamServiceImpl(modelMapper, streamRepository, tagRepository);
        stream = Stream.builder().id(1)
                                 .file_name("test")
                                 .build();
        streamDTO = StreamDTO.builder().id(1)
                                 .file_name("test")
                                 .build();
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
    @Test
    @DisplayName("get streams by tag")
    void testGetStreamsByTag(){
        List<Stream> list1 = new ArrayList<>();
        StreamTopDTO[] list2 = {};
        when(tagRepository.getStreamsByTag("sport")).thenReturn(list1);
        when(modelMapper.map(list1, StreamTopDTO[].class)).thenReturn(list2);
        assertEquals(streamService.getStreamsByTag("sport").size(), 0);
    }

    @Test
    @DisplayName("get stream by id")
    void testGetStreamById(){
        when(streamRepository.findById(1L)).thenReturn(Optional.of(stream));
        when(modelMapper.map(stream, StreamDTO.class)).thenReturn(streamDTO);
        assertEquals(streamService.getStreamById(1).getId(), stream.getId());
    }

    @Test
    @DisplayName("get stream of a specific student")
    void testGetUserStream(){
        List<Stream> list = new ArrayList<>();
        StreamDTO[] listDTO = {};
        when(streamRepository.findByOwnerId(2)).thenReturn(list);
        when(modelMapper.map(list, StreamDTO[].class)).thenReturn(listDTO);
        assertEquals(streamService.getUserStream(2).size(), 0);
    }
}
