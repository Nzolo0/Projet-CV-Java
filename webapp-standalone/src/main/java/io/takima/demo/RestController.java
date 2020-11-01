package io.takima.demo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.takima.demo.Classes.Profile;
import io.takima.demo.DAO.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.google.api.ResourceProto.resource;

/**
 *
 */
@RequestMapping(path = "/api")
@Controller
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

    @GetMapping(path = "/downloadProfile")
    public ResponseEntity<String> getfile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(getProfile());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "profile"+ "\"")
                .body(jsonString)
                ;
    }

    @PostMapping("/uploadProfile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        InputStream input = file.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        try{
            Profile profile=mapper.readValue(input, Profile.class);
            //todo : tester les entrées avant de delete
            deleteProfile();

            // tester les entrées
            uploadProfile(profile);

        }catch (Exception e ){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","Une erreur est survenue lors de l'upload du fichier");
        }

        redirectAttributes.addFlashAttribute("message","successfully uploaded !");
        return "redirect:/";

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

    private void uploadProfile(Profile profile){

        userDAO.saveAll(profile.getUser());
        educationDAO.saveAll(profile.getEducation());
        experienceDAO.saveAll(profile.getExperience());
        hobbyDAO.saveAll(profile.getHobby());
        presentationDAO.saveAll(profile.getPresentation());
        projectDAO.saveAll(profile.getProject());
        skillDAO.saveAll(profile.getSkills());
    }

    private void deleteProfile(){
        userDAO.deleteAll();
        educationDAO.deleteAll();
        experienceDAO.deleteAll();
        hobbyDAO.deleteAll();
        presentationDAO.deleteAll();
        projectDAO.deleteAll();
        skillDAO.deleteAll();
        userDAO.deleteAll();
    }



}
