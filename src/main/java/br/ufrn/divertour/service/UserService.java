package br.ufrn.divertour.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.repository.UserRepository;
import br.ufrn.divertour.service.exception.PhotoSavingException;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class UserService {

	private final UserRepository userRepository;

	private static final String PROFILE_PHOTO_FOLDER = "src/main/resources/common/img/profiles/";

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

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

	public void savePhoto(User user, UploadedFile selectedPhoto) throws PhotoSavingException {
		try {
			String originalFileName = selectedPhoto.getFileName();
			String[] splittedFileName = originalFileName.split(".");
			String fileExtension = splittedFileName[splittedFileName.length - 1];

			String outputFileName = user.getId() + "." + fileExtension;

			InputStream inputStream = selectedPhoto.getInputstream();
			FileOutputStream outputStream = new FileOutputStream(PROFILE_PHOTO_FOLDER + outputFileName);

			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			while (true) {
				bytesRead = inputStream.read(buffer);
				if (bytesRead > 0) {
					outputStream.write(buffer, 0, bytesRead);
				} else {
					break;
				}
			}
			outputStream.close();
			inputStream.close();

			user.setProfileImage(outputFileName);
			userRepository.save(user);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PhotoSavingException("Ocorreu um erro ao tentar salvar a foto. Tente atualizá-la mais tarde.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhotoSavingException("Ocorreu um erro ao tentar salvar a foto. Tente atualizá-la mais tarde.");
		}
	}

}