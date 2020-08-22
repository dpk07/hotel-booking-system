package com.deepak.HotelBooking.service;

import com.deepak.HotelBooking.model.Receptionist;
import com.deepak.HotelBooking.repository.ReceptionistRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ReceptionistService implements UserDetailsService {
    private final ReceptionistRepository receptionistRepository;

    public ReceptionistService(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    @Override
    public Receptionist loadUserByUsername(String s) throws UsernameNotFoundException {
        Receptionist receptionist = receptionistRepository.findByUserName(s);
        if(receptionist==null) throw new UsernameNotFoundException("Could not find username");
        return receptionist;
    }
}
