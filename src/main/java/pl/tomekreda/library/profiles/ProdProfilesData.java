package pl.tomekreda.library.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.BookCategory;
import pl.tomekreda.library.repository.BookCategoryRepository;

import javax.transaction.Transactional;

@Component
@Transactional
@ProdProfile
public class ProdProfilesData implements CommandLineRunner {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Override
    public void run(String... args) throws Exception {
        this.createBookCategory();

    }

    private void createBookCategory() {

        BookCategory bookCategory = new BookCategory("Fantasy");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Biografie/Autobiografie");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Młodzieżowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Naukowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Sportowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Bajka");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Historyczna");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Horror");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Przygodowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Inna");
        bookCategoryRepository.save(bookCategory);

    }
}
