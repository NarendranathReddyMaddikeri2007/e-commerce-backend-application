package com.backend.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {

    @Id //Primary Key to Entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob //Large object
    private Blob image;
    private String downloadUrl;

    //Many images there for one product OR One product can have many images
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
