package com.parking.lot.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.lot.entity.User;
import com.parking.lot.repo.UserRepo;
import com.parking.lot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;

	@Override
	public Optional<User> findById(long id) {
		return userRepo.findById(id);
	}

}
