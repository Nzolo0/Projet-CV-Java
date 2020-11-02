package io.takima.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.takima.demo.classes.Profile;
import io.takima.demo.dao.*;
import io.takima.demo.mail.EmailServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller use to send mail, upload/ download profile
 */
@RequestMapping(path = "/api")
@Controller
public class RestController {

    private final UserDAO userDAO;
    private final HobbyDAO hobbyDAO;
    private final EducationDAO educationDAO;
    private final SkillDAO skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;
    private final EmailServiceImpl emailService;


    public RestController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, EmailServiceImpl emailService) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
        this.emailService = emailService;
    }

    /**
     * Endpoint use to download json profile file
     * @return
     * @throws IOException
     */
    @GetMapping(path = "/downloadProfile")
    public ResponseEntity<String> getfile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(getProfile());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "profile" + "\"")
                .body(jsonString)
                ;
    }

    /**
     * Endpoint use to upload a profile
     * @param file
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadProfile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        InputStream input = file.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Profile profile = mapper.readValue(input, Profile.class);
            //todo : tester les entrées avant de delete
            deleteProfile();

            // tester les entrées
            uploadProfile(profile);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de l'upload du fichier");
        }

        redirectAttributes.addFlashAttribute("message", "successfully uploaded !");
        return "redirect:/";

    }

    /**
     * Endpoint use to send mail to the user
     * @param mail
     * @return
     * @throws MessagingException
     */
    @PostMapping("/contact")
    public RedirectView contactAdmin(@ModelAttribute Mail mail) throws MessagingException {
        sendMail(mail);
        return new RedirectView("/");
    }

    /**
     * Create a mail object and use EmailService to send mail
     * @param mail
     * @throws MessagingException
     */
    private void sendMail(Mail mail) throws MessagingException {
        mail.setTo(getCurrentUser().getEmail());
        Map<String, Object> templateModel = new HashMap<>();

        templateModel.put("senderName", mail.getSenderName());
        templateModel.put("senderMail", mail.getSenderMail());
        templateModel.put("subject", mail.getSubject());
        templateModel.put("text", mail.getText());
        templateModel.put("receiverName", getCurrentUser().getLastName());
        emailService.sendMessageUsingThymeleafTemplate(mail.getTo(), mail.getSubject(), templateModel);
    }

    /**
     * Extract User's Profile
     * @return Profile
     */
    public User getCurrentUser() {
    return userDAO.findAll().iterator().next();
    }

    /**
     * Extract User's Profile
     * @return Profile
     */
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

    /**
     * Save the Profile in the database
     * @param profile
     */
    private void uploadProfile(Profile profile) {

        userDAO.saveAll(profile.getUser());
        educationDAO.saveAll(profile.getEducation());
        experienceDAO.saveAll(profile.getExperience());
        hobbyDAO.saveAll(profile.getHobby());
        presentationDAO.saveAll(profile.getPresentation());
        projectDAO.saveAll(profile.getProject());
        skillDAO.saveAll(profile.getSkills());
    }

    /**
     * Erase the profile from the Database
     */
    private void deleteProfile() {
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
