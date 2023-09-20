package com.parking.lot.service;

import java.util.Optional;

import com.parking.lot.entity.User;

public interface UserService {
	Optional<User> findById(long id);
}
