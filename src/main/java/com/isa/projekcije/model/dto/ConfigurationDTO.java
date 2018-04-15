package com.isa.projekcije.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationDTO {
    private Long idAuditorium;
    private String nameAuditorium;
    private List<SegmentConfigDTO> segments;

    public ConfigurationDTO() {
        this.segments = new ArrayList<SegmentConfigDTO>();
    }

    public ConfigurationDTO(Long idAuditorium, String nameAuditorium, List<SegmentConfigDTO> segments) {
        this.idAuditorium = idAuditorium;
        this.nameAuditorium = nameAuditorium;
        this.segments = segments;
    }

    public Long getIdAuditorium() {
        return idAuditorium;
    }

    public void setIdAuditorium(Long idAuditorium) {
        this.idAuditorium = idAuditorium;
    }

    public String getNameAuditorium() {
        return nameAuditorium;
    }

    public void setNameAuditorium(String nameAuditorium) {
        this.nameAuditorium = nameAuditorium;
    }

    public List<SegmentConfigDTO> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentConfigDTO> segments) {
        this.segments = segments;
    }
}
