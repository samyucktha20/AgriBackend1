package com.projectkisan.model;

import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisRequest {

    @NotNull
    private Long farmerId;
    private String cropType;
    private String symptoms;
    private String imageBase64;
    private String location;
}