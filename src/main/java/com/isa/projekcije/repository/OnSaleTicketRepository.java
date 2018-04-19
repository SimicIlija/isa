package com.isa.projekcije.repository;

import com.isa.projekcije.model.OnSaleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnSaleTicketRepository extends JpaRepository<OnSaleTicket, Long> {

    public List<OnSaleTicket> findByTicketProjectionAuditoriumInstitutionIdAndTicketReserved(Long idInstitution, boolean reserved);

    public List<OnSaleTicket> findByTicketProjectionShowId(Long idShow);

    public List<OnSaleTicket> findByTicketProjectionIdAndTicketSeatSegmentId(Long idProjection, Long idSegment);
}
