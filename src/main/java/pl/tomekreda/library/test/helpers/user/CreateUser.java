package pl.tomekreda.library.test.helpers.user;

import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserState;
import pl.tomekreda.library.profiles.util.TestingProfilesUtils;

public class CreateUser {

    public static User createLibraryOwner(){
        User owner = new User("Tomekk", "Redaa", "owner@local", 123456789, TestingProfilesUtils.DATAP, UserState.ACTIVE);
        return  owner;
    }
}
