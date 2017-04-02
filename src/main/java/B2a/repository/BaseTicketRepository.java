package B2a.repository;

import B2a.domain.Ticket.BaseTicket;
import org.springframework.data.repository.CrudRepository;

public interface BaseTicketRepository extends CrudRepository<BaseTicket, Long> {

    @Override
    Iterable<BaseTicket> findAll();
}

