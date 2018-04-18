package com.isa.projekcije.converters;

import com.isa.projekcije.model.ProjectionRating;
import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ShowDTO;
import com.isa.projekcije.service.ProjectionRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowToShowDTOConverter implements Converter<Show, ShowDTO> {

    @Autowired
    private ProjectionRatingService projectionRatingService;

    @Override
    public ShowDTO convert(Show show) {
        if (show == null) {
            return null;
        }
        ShowDTO showDTO = new ShowDTO(show.getId(), show.getName(), show.getGenre(), show.getProducer(),
                show.getDuration(), show.getPosterFileName(), show.getInstitution().getId(), show.getDescription());
        List<ProjectionRating> projectionRatings = projectionRatingService.findByShow(show.getId());
        double rating = 0;
        for (ProjectionRating projectionRating : projectionRatings) {
            rating += projectionRating.getProjectionRating();
        }
        rating = rating / projectionRatings.size();
        showDTO.setRating(rating);

        return showDTO;

    }

    public List<ShowDTO> convert(List<Show> showList) {
        List<ShowDTO> showDTOList = new ArrayList<ShowDTO>();
        for (Show show : showList) {
            showDTOList.add(convert(show));
        }
        return showDTOList;
    }
}
