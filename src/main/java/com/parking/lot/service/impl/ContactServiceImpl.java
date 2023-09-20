package com.parking.lot.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.lot.entity.Contact;
import com.parking.lot.repo.ContactRepo;
import com.parking.lot.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	ContactRepo contactRepo;

	@Override
	public Optional<Contact> findById(Long id) {
		return contactRepo.findById(id);
	}

}
