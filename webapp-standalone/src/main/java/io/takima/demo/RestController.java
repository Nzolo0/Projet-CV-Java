package io.takima.demo;


import io.takima.demo.Classes.Profile;
import io.takima.demo.DAO.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@org.springframework.web.bind.annotation.RestController

@RequestMapping(path = "/data")
public class RestController {

    private final UserDAO userDAO;
    private final HobbyDAO hobbyDAO;
    private final EducationDAO educationDAO;
    private final SkillDAO  skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;



    public RestController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
    }


    @GetMapping(path = "/data")
    public Profile getJsonData(){
        return getProfile();
    }

    private Profile getProfile() {

        Profile profile = new Profile();
        profile.setEducation(educationDAO.findAll());
        profile.setExperience(experienceDAO.findAll());
        profile.setHobby(hobbyDAO.findAll());
        profile.setPresentation(presentationDAO.findAll());
        profile.setProject(projectDAO.findAll());
        profile.setSkills(skillDAO.findAll());
        profile.setUser(userDAO.findAll());
        return profile;
    }


}
