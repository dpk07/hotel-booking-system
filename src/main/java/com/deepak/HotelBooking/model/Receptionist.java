package com.deepak.HotelBooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
public class Receptionist extends BaseEntity implements UserDetails {
    @ManyToOne(optional = false)
    @JsonIgnore
    private Hotel hotel;
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    @JsonIgnore
    private String userName;

    @NotBlank
    @JsonIgnore
    private String password;
    public Receptionist() {
    }
    public Receptionist(Long id){
        this.setId(id);
    }
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
