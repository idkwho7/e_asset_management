package com.easset.service;

import java.util.List;
import java.util.regex.Pattern;

import com.easset.entity.User;
import com.easset.exceptions.AssetNotFoundException;
import com.easset.exceptions.UserAlreadyExistsException;
import com.easset.exceptions.UserBannedException;
import com.easset.exceptions.UserInputValidationException;
import com.easset.exceptions.UserNotFoundException;
import com.easset.storage.UserStorage;
import com.easset.storage.UserStorageImpl;

public class UserServiceImpl implements UserService {
	UserStorage userStorageObject = new UserStorageImpl();
	
	@Override
	public boolean addUser(User u) throws UserAlreadyExistsException, UserInputValidationException {
		if (u.getName() == null || u.getName().isEmpty() ||
            u.getRole() == null || u.getRole().isEmpty() ||
            u.getTelephone() == null || u.getTelephone().isEmpty() ||
            u.getEmail() == null || u.getEmail().isEmpty() ||
            u.getUsername() == null || u.getUsername().isEmpty() ||
            u.getPassword() == null || u.getPassword().isEmpty()) {
            throw new UserInputValidationException("All fields are mandatory.");
        }

        // Validate phone number format using regex
        String phoneRegex = "^\\d{10}$"; // Assuming a 10-digit phone number
        if (!Pattern.matches(phoneRegex, u.getTelephone())) {
            throw new UserInputValidationException("Invalid phone number format.");
        }

        // Validate email format using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; // Basic email format regex
        if (!Pattern.matches(emailRegex, u.getEmail())) {
            throw new UserInputValidationException("Invalid email format.");
        }
        
        boolean addedStatus = userStorageObject.addUser(u);
        if (addedStatus == true) {
        	return true;
        }
		throw new UserAlreadyExistsException("User with the same username or email already exists.");
	}

	@Override
	public User getUser(int userId) throws UserNotFoundException {
		User u = userStorageObject.getUser(userId);
		if (u != null) {
			return u;
		}
		throw new UserNotFoundException("The user with id " + userId + "doesn't exist.");
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userStorageObject.getAllUsers();
	}

	@Override
	public int bannedTill(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
