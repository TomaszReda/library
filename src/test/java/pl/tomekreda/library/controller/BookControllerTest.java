package pl.tomekreda.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.tomekreda.library.repository.*;
import pl.tomekreda.library.request.AddBookRequest;
import pl.tomekreda.library.service.BookService;
import pl.tomekreda.library.service.UserService;
import pl.tomekreda.library.test.helpers.bookCategory.CreateBookCategory;
import pl.tomekreda.library.test.helpers.library.CreateLibrary;
import pl.tomekreda.library.test.helpers.user.CreateUser;


import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringRunner.class)
public class BookControllerTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/snippets");

    private MockMvc mockMvc;

    private BookController bookController;

    @InjectMocks
    private BookService bookService;

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private UserService userService;

    @Mock
    BookCategoryRepository bookCategoryRepository;

    @Mock
    BookRepository bookRepository;

    private AddBookRequest addBookRequest = new AddBookRequest(UUID.randomUUID(), "Author", "Tytul", "Publisher", LocalDate.now(), "isbn", 0, "description", "bookCategory");


    @Before
    public void setUp() throws Exception {
        bookController = new BookController(bookService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .apply(documentationConfiguration(this.restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080))
                .build();
    }

    @Test
    public void addBook() throws Exception {
        Mockito.when(userService.findLoggedUser()).thenReturn(CreateUser.createLibraryOwner());
        Mockito.when(libraryRepository.findById(Mockito.any())).thenReturn(Optional.of(CreateLibrary.createLibray()));
        Mockito.when(bookCategoryRepository.findFirstByCategoryType(Mockito.any())).thenReturn(CreateBookCategory.bookCategory());
        this.mockMvc.perform(post("/api/book/add").contentType(MediaType.APPLICATION_JSON).content("{\"author\":\"test\",\"title\":\"test\",\"publisher\":\"test\",\"date\":\"0022-02-22\",\"isbn\":\"test\",\"description\":\"tedst\",\"bookCategory\":\"Fantasy\",\"quant\":2,\"libraryId\":\"01a5cf04-1ea7-4dec-ac38-c8d69131dff9\"}"))
                .andDo(print())
                .andDo(document("addBookByLibraryOwner"))
                .andExpect(status().isOk());
    }


}

