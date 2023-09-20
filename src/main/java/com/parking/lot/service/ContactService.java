package com.parking.lot.service;

import java.util.Optional;

import com.parking.lot.entity.Contact;

public interface ContactService {
	Optional<Contact> findById(Long id);
}
