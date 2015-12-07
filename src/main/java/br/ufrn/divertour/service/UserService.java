package br.ufrn.divertour.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.repository.IUserRepository;
import br.ufrn.divertour.service.exception.PhotoSavingException;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class UserService {

	private static final String USER_PHOTO_FOLDER = "/Users/lucascriistiano/webapp/images/users";
	
	@Autowired
	private IUserRepository userRepository;
	
	private void validate(User user) throws ValidationException {
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new ValidationException("O e-mail já se encontra cadastrado");
		}

		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new ValidationException("O nome de usuário já se encontra em uso");
		}
	}

	public void register(User user) throws ValidationException {
		validate(user);
		userRepository.save(user);
	}

	public void update(User user) throws ValidationException {
		validate(user);
		userRepository.save(user);
	}

	public void remove(String id) {
		userRepository.delete(id);
	}

	public void changePermission(String id, boolean admin) {
		User user = userRepository.findById(id);
		user.setAdmin(admin);
		userRepository.save(user);
	}

	public User findById(String id) {
		return userRepository.findById(id);
	}

	public List<User> listAll() {
		return userRepository.findAll();
	}

	public User checkLogin(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

	public void saveUserPhoto(User user, UploadedFile uploadedPhoto) throws PhotoSavingException {
		try {
			String uploadedPhotoFilename = uploadedPhoto.getFileName();

			Path folder = Paths.get(USER_PHOTO_FOLDER);
			String filename = FilenameUtils.getBaseName(uploadedPhotoFilename);
			String extension = FilenameUtils.getExtension(uploadedPhotoFilename);
			Path file = Files.createTempFile(folder, filename + "-", "." + extension);

			InputStream input = uploadedPhoto.getInputstream();
			
			Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
			
			String createdPhoto = file.getFileName().toString();
			user.setProfileImage(createdPhoto);
			this.userRepository.save(user);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PhotoSavingException("Ocorreu um erro ao tentar salvar a foto. Tente atualizá-la mais tarde.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhotoSavingException("Ocorreu um erro ao tentar salvar a foto. Tente atualizá-la mais tarde.");
		}
	}

}