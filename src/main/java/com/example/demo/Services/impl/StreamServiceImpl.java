package com.example.demo.Services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.Models.DTO.Stream.StreamTopDTO;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Services.StreamService;

@Service
public class StreamServiceImpl implements StreamService{
    private final StreamRepository streamRepository;
    private final ModelMapper modelMapper;

    public StreamServiceImpl(
        ModelMapper modelMapper,
        StreamRepository streamRepository
    ){
        this.modelMapper = modelMapper;
        this.streamRepository = streamRepository;
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
}
