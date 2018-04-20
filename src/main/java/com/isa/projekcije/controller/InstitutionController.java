package com.isa.projekcije.controller;

import com.isa.projekcije.converters.InstitutionDTOToInstitutionConverter;
import com.isa.projekcije.converters.InstitutionToInstitutionDTOConverter;
import com.isa.projekcije.model.*;
import com.isa.projekcije.model.dto.*;
import com.isa.projekcije.service.InstitutionService;
import com.isa.projekcije.service.ProjectionRatingService;
import com.isa.projekcije.service.ReservationsService;
import com.isa.projekcije.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionToInstitutionDTOConverter institutionToInstitutionDTOConverter;

    @Autowired
    private InstitutionDTOToInstitutionConverter institutionDTOToInstitutionConverter;

    @Autowired
    private ProjectionRatingService projectionRatingService;

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/getById/{idInstitution}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByInstitution(@PathVariable Long idInstitution) {
        Institution institution = institutionService.findOne(idInstitution);
        if (institution == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        InstitutionDTO institutionDTO = institutionToInstitutionDTOConverter.convert(institution);
        List<ProjectionRating> projectionRatings = projectionRatingService.findByInstitution(idInstitution);
        double rating = 0;
        for (ProjectionRating projectionRating : projectionRatings) {
            rating += projectionRating.getInstitutionRating();
        }
        rating = rating / projectionRatings.size();
        institutionDTO.setRating(rating);
        return new ResponseEntity<>(institutionDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getInstitutions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInstitutions() {
        List<Institution> institutions = institutionService.findAll();
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institutions), HttpStatus.OK);
    }

    @RequestMapping(
            value = "getCinemas",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getCinemas() {
        List<Institution> institutions = institutionService.getCinemas();
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institutions), HttpStatus.OK);
    }

    @RequestMapping(
            value = "getTheatres",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getTheatres() {
        List<Institution> institutions = institutionService.getTheatres();
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(institutions), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_SYS')")
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public ResponseEntity<?> addInstitution(@Valid @RequestBody InstitutionDTO institutionDTO) {
        System.out.println(institutionDTO.isCinema());
        Institution newInstitution = institutionService.save(institutionDTOToInstitutionConverter.convert(institutionDTO));
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(newInstitution), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/editInstitution/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody InstitutionDTO institutionDTO) {
        Institution edited = institutionService.findOne(id);
        if (edited == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        edited.setName(institutionDTO.getName());
        edited.setLongitude(institutionDTO.getLongitude());
        edited.setLatitude(institutionDTO.getLatitude());
        edited.setDescription(institutionDTO.getDescription());
        Institution newInstitution = institutionService.save(edited);
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(newInstitution), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Institution deleted = institutionService.delete(id);
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(institutionToInstitutionDTOConverter.convert(deleted), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getInstitutionsByAdmin",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getInstitutionsByAdmin() {
        User loggedIn = userService.getCurrentUser();
        if (loggedIn.getRole().equals(Role.ADMIN_INST)) {
            InstitutionAdmin user = (InstitutionAdmin) loggedIn;
            List<InstitutionDTO> institutionDTOList = institutionToInstitutionDTOConverter.convert(user.getInstitutions());
            if (user.getInstitutions() != null) {
                return new ResponseEntity<>(institutionDTOList, HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/getCharts/{idInstitution}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> idInstitution(@PathVariable Long idInstitution) {
        HashMap<String, Integer> dayChart = new HashMap<String, Integer>();
        HashMap<String, Integer> weekChart = new HashMap<String, Integer>();
        HashMap<String, Integer> monthChart = new HashMap<String, Integer>();
        for (int i = 10; i < 23; i++) {
            dayChart.put(i + "h", 0);
        }
        List<String> weekdays = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        for (String weekday : weekdays) {
            weekChart.put(weekday, 0);
        }
        List<String> months = Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        for (String month : months) {
            monthChart.put(month, 0);
        }
        List<Reservation> reservations = reservationsService.findByInstitution(idInstitution);
        for (Reservation reservation : reservations) {
            Date projectionDate = reservation.getProjection().getDate();
            LocalDateTime localDate = projectionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            int hourNumber = dayChart.get(localDate.getHour() + "h");
            hourNumber += reservation.getTickets_reserved().size();
            dayChart.put(localDate.getHour() + "h", hourNumber);

            int weekNumber = weekChart.get(localDate.getDayOfWeek().toString());
            weekNumber += reservation.getTickets_reserved().size();
            weekChart.put(localDate.getDayOfWeek().toString(), weekNumber);

            int monthNumber = monthChart.get(localDate.getMonth().toString());
            monthNumber += reservation.getTickets_reserved().size();
            monthChart.put(localDate.getMonth().toString(), monthNumber);
        }

        List<ChartDTO> chartDTOS = new ArrayList<ChartDTO>();
        ChartDTO dayChartDTO = new ChartDTO(new ArrayList<ChartValueDTO>());
        ChartDTO weekChartDTO = new ChartDTO(new ArrayList<ChartValueDTO>());
        ChartDTO monthChartDTO = new ChartDTO(new ArrayList<ChartValueDTO>());

        for (int i = 10; i < 23; i++) {
            ChartValueDTO chartValueDTO = new ChartValueDTO(i + "h", dayChart.get(i + "h"));
            dayChartDTO.getValues().add(chartValueDTO);
        }
        for (int i = 0; i < weekdays.size(); i++) {
            ChartValueDTO chartValueDTO = new ChartValueDTO(weekdays.get(i), weekChart.get(weekdays.get(i)));
            weekChartDTO.getValues().add(chartValueDTO);
        }
        for (int i = 0; i < months.size(); i++) {
            ChartValueDTO chartValueDTO = new ChartValueDTO(months.get(i), monthChart.get(months.get(i)));
            monthChartDTO.getValues().add(chartValueDTO);
        }
        chartDTOS.add(dayChartDTO);
        chartDTOS.add(weekChartDTO);
        chartDTOS.add(monthChartDTO);

        return new ResponseEntity<>(chartDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN_INST')")
    @RequestMapping(
            value = "/getIncome/{idInstitution}",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> getIncome(@PathVariable Long idInstitution, @RequestBody IncomeDTO incomeDTO) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = formatter.parse(incomeDTO.getDateFrom());
            dateTo = formatter.parse(incomeDTO.getDateTo());
            double income = 0;
            List<Reservation> reservations = reservationsService.findByInstitution(idInstitution);

            for (Reservation reservation : reservations) {
                if (reservation.getProjection().getDate().before(dateTo) && reservation.getProjection().getDate().after(dateFrom))
                    for (Ticket ticket : reservation.getTickets_reserved()) {
                        income += ticket.getPrice().doubleValue();
                    }
            }

            String incomeStr = income + "";
            return new ResponseEntity<>(incomeStr, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN_SYS')")
    @RequestMapping(value = "/{idins}/adm/{ida}", method = RequestMethod.POST)
    public ResponseEntity addNewAdminToIns(@PathVariable("idins") long idins,
                                           @PathVariable("ida") long ida) {
        System.out.println(idins);
        System.out.println(ida);
        try {
            Institution institution = institutionService.findOne(idins);
            if (institution == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            InstitutionAdmin institutionAdmin = (InstitutionAdmin) userService.findById(ida);
            boolean bool1 = institution.addAdmin(institutionAdmin);
            boolean bool2 = institutionAdmin.addInstitution(institution);
            if (!(bool1 || bool2)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            userService.save(institutionAdmin);
            institutionService.save(institution);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}/admins", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity adminsOfIns(@PathVariable("id") long id) {
        try {
            Institution institution = institutionService.findOne(id);
            if (institution == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            List<InstitutionAdmin> admins = institution.getInstitutionAdmins();
            List<RoleDto> retVal = admins.stream().map(RoleDto::createRoleDto).collect(Collectors.toList());
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}/freeadmins", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity freeAdminsForIns(@PathVariable("id") long id) {
        try {
            Institution institution = institutionService.findOne(id);
            if (institution == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            List<User> insAdmins = userService.findInsAdmins();
            insAdmins = insAdmins.stream().filter(ia -> !institution.haveAdmin(ia.getId())).collect(Collectors.toList());
            List<RoleDto> retVal = insAdmins.stream().map(RoleDto::createRoleDto).collect(Collectors.toList());
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
