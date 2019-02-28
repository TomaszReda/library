package pl.tomekreda.library.profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.profiles.util.TestingData;

import javax.transaction.Transactional;

@Component
@Transactional
@ProdProfile
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdProfilesData implements CommandLineRunner {



    private final TestingData testingData;

    @Override
    public void run(String... args) throws Exception {
        testingData.createBookCategory();
        testingData.createTemplate();

    }



}
