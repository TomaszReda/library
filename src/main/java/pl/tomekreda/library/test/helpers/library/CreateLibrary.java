package pl.tomekreda.library.test.helpers.library;

import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.profiles.util.TestingProfilesUtils;
import pl.tomekreda.library.test.helpers.user.CreateUser;

public class CreateLibrary {
    public static Library createLibray() {
        User owner= CreateUser.createLibraryOwner();
        return new Library(null, "Chrustne", TestingProfilesUtils.EMAIL_TOMEK, "51.61308", null, "21.97838", "Marzenie" , "34", "08-500 Ryki", null, null, null, owner.getUserMenager(), null);
    }
}
