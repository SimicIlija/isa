package com.isa.projekcije.service;

import com.isa.projekcije.model.OnSaleTicket;
import com.isa.projekcije.repository.OnSaleTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnSaleTicketService {

    @Autowired
    private OnSaleTicketRepository onSaleTicketRepository;

    public OnSaleTicket save(OnSaleTicket onSaleTicket) {
        return onSaleTicketRepository.save(onSaleTicket);
    }

    public List<OnSaleTicket> findByInstitutionAndTicketReserved(Long idInstitution, boolean reserved) {
        return onSaleTicketRepository.findByTicketProjectionAuditoriumInstitutionIdAndTicketReserved(idInstitution, reserved);
    }

    public List<OnSaleTicket> findByShow(Long idShow) {
        return onSaleTicketRepository.findByTicketProjectionShowId(idShow);
    }

    public List<OnSaleTicket> findByTicketProjectionIdAndTicketSeatSegmentId(Long idProjection, Long idSegment) {
        return onSaleTicketRepository.findByTicketProjectionIdAndTicketSeatSegmentId(idProjection, idSegment);
    }

    public OnSaleTicket findOne(Long idOnSaleTicket) {
        return onSaleTicketRepository.findOne(idOnSaleTicket);
    }

    public OnSaleTicket delete(Long idOnSaleTicket) {
        OnSaleTicket onSaleTicket = onSaleTicketRepository.findOne(idOnSaleTicket);
        if (onSaleTicket == null) {
            return null;
        }
        onSaleTicketRepository.delete(onSaleTicket);
        return onSaleTicket;
    }

}
