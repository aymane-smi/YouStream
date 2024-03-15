package com.example.demo.Models.DTO.Stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StreamTopDTO {
    private int id;
    private String fileName;
    private String username;
    private int viewsNbr;
}
