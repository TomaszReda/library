package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional

public class SearchAdminService {

    private final UserRepository userRepository;

    private final LibraryRepository libraryRepository;

    private final BookRepository bookRepository;

    public ResponseEntity searchAllUser(int page, int size) {
        try {
            Pageable pageableRequest = PageRequest.of(page, size);

            Page<User> userPage = userRepository.findAll(pageableRequest);
            log.info("[Search admin]=" + userPage);
            return ResponseEntity.ok(userPage);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity searchUser(String word, int page, int size) {
        try {
            Pageable pageableRequest = PageRequest.of(page, size);
            Page<User> user = userRepository.findAllByEmailContains(word, pageableRequest);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity searchAllLibrary(int page, int size) {
        try {
            Pageable pageableRequest = PageRequest.of(page, size);

            Page<Library> userPage = libraryRepository.findAll(pageableRequest);
            log.info("[Search admin]=" + userPage);
            return ResponseEntity.ok(userPage);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity searchLibrary(String word, int page, int size) {
        try {
            Pageable pageableRequest = PageRequest.of(page, size);
            Page<Library> user = libraryRepository.findAllByNameContains(word, pageableRequest);

            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


}
