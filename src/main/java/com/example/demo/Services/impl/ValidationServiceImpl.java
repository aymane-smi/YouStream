package com.example.demo.Services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Models.DTO.Stream.StreamCreateDTO;
import com.example.demo.Models.DTO.Stream.StreamDTO;
import com.example.demo.Models.DTO.Stream.StreamResponseDTO;
import com.example.demo.Models.DTO.Tag.TagDTO;
import com.example.demo.Models.Entites.Stream;
import com.example.demo.Models.Entites.Tag;
import com.example.demo.Repositories.StreamRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.TagRepository;
import com.example.demo.Services.ValidationService;
import com.example.demo.Helper.Security;

@Service
public class ValidationServiceImpl implements ValidationService{

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final StreamRepository streamRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public ValidationServiceImpl(
        StudentRepository studentRepository,
        PasswordEncoder passwordEncoder,
        StreamRepository streamRepository,
        TagRepository tagRepository,
        ModelMapper modelMapper
        ){
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.streamRepository = streamRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StreamResponseDTO createStream(StreamCreateDTO streamDTO){
        List<Tag> tags = tagManager(streamDTO.getTags());
        Base64 base64 = new Base64();
        var tmpStream = Stream.builder().viewsNbr(0)
                                        .restricted(false)
                                        .tags(tags)
                                        .owner(
                                            studentRepository.findByUsername(Security.retriveUsername()).get()
                                        ).build();
        var stream = streamRepository.save(tmpStream);
        String token = Security.retriveUsername()+"-"+System.currentTimeMillis()+"-"+stream.getId();
        return StreamResponseDTO.builder().stream(
                                            modelMapper.map(stream, StreamDTO.class)
                                          ).StreamToken(
                                            base64.encodeBase64String(token.getBytes())
                                            ).build();
    }

    @Override
    public boolean validation(String key, String password) {
        Base64 base64 = new Base64();
        String[] decodedKey = new String(base64.decode(key.getBytes())).split("-");
        System.out.println(key);
        var student = studentRepository.findByUsername(decodedKey[0]).get();
        if(passwordEncoder.matches(password, student.getPassword())){
            var stream = streamRepository.findById(Long.parseLong(decodedKey[2])).get();
            stream.setFile_name(key);
            streamRepository.save(stream);
            return true;
        }
        return false;
    }

    private List<Tag> tagManager(List<TagDTO> tags){
        List<Tag> resultTags = new ArrayList<>();
        for(TagDTO tag:tags)
            if(tag.getId() == 0)
            resultTags.add(tagRepository.save(
                    modelMapper.map(tag, Tag.class)
                ));
            else
                resultTags.add(
                    modelMapper.map(tag, Tag.class)
                );
        return resultTags;
    }

    // private String Security.retriveUsername(){
    //     Authentication auth = authentication.getAuthentication();
    //     if(authentication != null && auth.isAuthenticated()){
    //         UserDetails userDetails = (UserDetails) auth.getPrincipal();
    //         return userDetails.getUsername();
    //     }
    //     return null;
    // }
    
}
