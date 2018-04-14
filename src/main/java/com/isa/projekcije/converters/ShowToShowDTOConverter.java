package com.isa.projekcije.converters;

import com.isa.projekcije.model.Show;
import com.isa.projekcije.model.dto.ShowDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowToShowDTOConverter implements Converter<Show, ShowDTO> {

    @Override
    public ShowDTO convert(Show show) {
        if (show == null) {
            return null;
        }
        return new ShowDTO(show.getId(), show.getName(), show.getGenre(), show.getProducer(),
                show.getDuration(), show.getPosterFileName(), show.getPosterData(), show.getInstitution().getId());

    }

    public List<ShowDTO> convert(List<Show> showList) {
        List<ShowDTO> showDTOList = new ArrayList<ShowDTO>();
        for (Show show : showList) {
            showDTOList.add(convert(show));
        }
        return showDTOList;
    }
}
