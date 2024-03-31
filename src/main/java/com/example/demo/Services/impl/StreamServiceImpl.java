package com.example.demo.Services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.Models.DTO.Stream.StreamDTO;
import com.example.demo.Models.DTO.Stream.StreamTopDTO;
import com.example.demo.Models.Entites.Stream;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Repositories.TagRepository;
import com.example.demo.Services.StreamService;

import io.jsonwebtoken.lang.Arrays;

@Service
public class StreamServiceImpl implements StreamService{
    private final StreamRepository streamRepository;
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    public StreamServiceImpl(
        ModelMapper modelMapper,
        StreamRepository streamRepository,
        TagRepository tagRepository
    ){
        this.modelMapper = modelMapper;
        this.streamRepository = streamRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Cacheable(value = "topStreams")
    public List<StreamTopDTO> getTopTenStream() {
        List<StreamTopDTO> list = new ArrayList<>();
        var topStreams = streamRepository.findFirst10ByOrderByViewsNbrDesc();
        topStreams.forEach(stream -> {
            var tmp = modelMapper.map(list, StreamTopDTO.class);
            tmp.setId((int)stream.getId());
            tmp.setFileName(stream.getFile_name());
            tmp.setUsername(stream.getOwner().getUsername());
            list.add(tmp);
        });
        return list;
    }

    @Override
    public List<StreamTopDTO> getAllStream(int offset) {
        PageRequest page = PageRequest.of(offset, 10);
        var result = streamRepository.findByRestrictedIs(false, page);
        return result.getContent().stream()
                           .map(stream -> {
                            var tmp = modelMapper.map(stream, StreamTopDTO.class);
                            tmp.setId((int)stream.getId());
                            tmp.setFileName(stream.getFile_name());
                            tmp.setUsername(stream.getOwner().getUsername());
                            return tmp;
                           })
                           .toList();
    }

    @Override
    public List<StreamTopDTO> getStreamsByTag(String tag) {
        return Arrays.asList(modelMapper.map(tagRepository.getStreamsByTag(tag), StreamTopDTO[].class));
    }

    @Override
    public StreamDTO getStreamById(int id){
        return modelMapper.map(streamRepository.findById((long)id).get(), StreamDTO.class);
    }

    @Override
    public List<StreamDTO> getUserStream(long id) {
        List<Stream> streams = streamRepository.findByOwnerId(id);
        return List.of(modelMapper.map(streams, StreamDTO[].class));
    }
}