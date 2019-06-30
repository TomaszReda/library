package pl.tomekreda.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;
import pl.tomekreda.library.quartz.service.ReminderOfGivingABookForLibraryService;
import pl.tomekreda.library.quartz.service.ReminderOfGivingABookForUserService;
import pl.tomekreda.library.repository.*;
import pl.tomekreda.library.request.AddBookRequest;
import pl.tomekreda.library.service.BookService;
import pl.tomekreda.library.service.UserService;
import pl.tomekreda.library.test.helpers.library.CreateLibrary;
import pl.tomekreda.library.test.helpers.user.CreateUser;


import javax.mail.internet.ContentType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs(outputDir = "build/snippets")
public class BookControllerTest {


    private MockMvc mockMvc;

    private BookController bookController;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private UserService userService;
    @Mock
    private TaskForUserRepository taskForUserRepository;
    @Mock
    private TaskForLibraryRepository taskForLibraryRepository;
    @Mock
    private ReceiveTheBookForUserService receiveTheBookForUserService;
    @Mock
    private ReminderOfGivingABookForLibraryService reminderOfGivingABookForLibraryService;
    @Mock
    private ReminderOfGivingABookForUserService reminderOfGivingABookForUserService;
    @Mock
    private MessageToCasualUserRepository messageToCasualUserRepository;
    @Mock
    private MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    private AddBookRequest addBookRequest = new AddBookRequest(UUID.randomUUID(), "Author", "Tytul", "Publisher", LocalDate.now(), "isbn", 0, "description", "bookCategory");


    private Library library;

    @Before
    public void setUp() throws Exception {
        bookController = new BookController(bookService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void addBook() throws Exception {
        Mockito.when(userService.findLoggedUser()).thenReturn(CreateUser.createLibraryOwner());
        Mockito.when(libraryRepository.findById(Mockito.any())).thenReturn(Optional.of(CreateLibrary.createLibray()));
        System.err.println(asJsonString(addBookRequest));
        this.mockMvc.perform(post("/api/book/add").contentType(MediaType.APPLICATION_JSON).content("{\"author\":\"test\",\"title\":\"test\",\"publisher\":\"test\",\"date\":\"0022-02-22\",\"isbn\":\"test\",\"description\":\"tedst\",\"bookCategory\":\"Fantasy\",\"quant\":2,\"libraryId\":\"01a5cf04-1ea7-4dec-ac38-c8d69131dff9\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

//               .andDo(document("addBookByLibraryOwner"))