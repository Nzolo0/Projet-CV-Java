package io.takima.demo;
import io.takima.demo.Classes.EducationWrapper;
import io.takima.demo.Classes.ExperienceWrapper;
import io.takima.demo.DAO.*;
import io.takima.demo.mail.EmailServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.util.*;

/**
 *
 */
@RequestMapping("/")
@Controller
public class LibraryController {

    private final UserDAO userDAO;
    private final HobbyDAO hobbyDAO;
    private final EducationDAO educationDAO;
    private final SkillDAO  skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;
    private final EmailServiceImpl emailService;
    private ArrayList<Experience> experienceList;
    private ArrayList<Education> educationList;



    public LibraryController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, EmailServiceImpl emailService, ArrayList<Experience> experienceList, ArrayList<Education> educationList) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
        this.emailService = emailService;
        this.experienceList = experienceList;
        this.educationList = educationList;
    }

    @GetMapping
    public String homePage(Model m) {

        Optional<User> optUser = userDAO.findById((long) 1);
        Optional<Presentation> optPresentation = presentationDAO.findById((long) 1);

        //TODO: add function

        if(optUser.isPresent() && optPresentation.isPresent()) {

            m.addAttribute("user",getCurrentUser());
            m.addAttribute("hobbies", hobbyDAO.findAll());
            m.addAttribute("education", sortEducations());
            m.addAttribute("skill", skillDAO.findAll());
            m.addAttribute("project", projectDAO.findAll());
            m.addAttribute("experience", sortExperiences());
            m.addAttribute("presentation", optPresentation.get());
            m.addAttribute("mail",new Mail());

            return "index";
        }
        else{
            return "404";
        }
    }

    @GetMapping("/admin")
    public String addUserPage(Model m) {

        sendAttributes(m);

        return "admin";
    }

    private void sendAttributes(Model m) {
        Optional<User> optUser = userDAO.findById((long) 1);
        Optional<Presentation> optPresentation = presentationDAO.findById((long) 1);

        //TODO: add function

        if(optUser.isPresent() && optPresentation.isPresent()) {

            m.addAttribute("user", optUser.get());
            m.addAttribute("expWrapper", getExperienceWrapper());
            m.addAttribute("eduWrapper", getEducationWrapper());
            m.addAttribute("hobbies", hobbyDAO.findAll());
            m.addAttribute("education", sortEducations());
            //         m.addAttribute("skill", skillDAO.findAll());
            m.addAttribute("project", projectDAO.findAll());
            m.addAttribute("experience", sortExperiences());
            m.addAttribute("presentation", optPresentation.get());

        }
    }

    @NotNull
    private ExperienceWrapper getExperienceWrapper() {
        experienceList = (ArrayList<Experience>) sortExperiences();
        ExperienceWrapper expWrapper = new ExperienceWrapper();
        expWrapper.setExperienceList(experienceList);
        return expWrapper;
    }

    @NotNull
    private EducationWrapper getEducationWrapper() {
        educationList = (ArrayList<Education>) sortEducations();
        EducationWrapper eduWrapper = new EducationWrapper();
        eduWrapper.setEducationList(educationList);
        return eduWrapper;
    }

    @PostMapping( value="/admin", params="submitUser")
    public String updateUser(@ModelAttribute User user, @ModelAttribute Presentation presentation, Model m) {

        user.setId((long) 1);

        userDAO.save(user);

        presentation.setId((long) 1);

        presentationDAO.save(presentation);

        m.addAttribute("user", user);
        m.addAttribute("hobbies", hobbyDAO.findAll());
        m.addAttribute("expWrapper", getExperienceWrapper());
        m.addAttribute("eduWrapper", getEducationWrapper());
        m.addAttribute("education", sortEducations());
        //         m.addAttribute("skill", skillDAO.findAll());
        m.addAttribute("project", projectDAO.findAll());
        m.addAttribute("experience", sortExperiences());
        m.addAttribute("presentation", presentation);

        return "redirect:/admin#about";
    }

    @PostMapping( value="/admin", params="submitExp")
    public String updateExp(@ModelAttribute ExperienceWrapper expWrapper, Model m) {

        experienceDAO.saveAll(expWrapper.getExperienceList());

        sendAttributes(m);

        return "redirect:/admin#experience";
    }

    @PostMapping( value="/admin", params="removeExp")
    public String deleteExp(@RequestParam Long expId, Model m) {

        experienceDAO.deleteById(expId);

        sendAttributes(m);

        return "redirect:/admin#experience";
    }

    @PostMapping( value="/admin", params="addExp")
    public String addExp(Model m) {

        experienceDAO.save(new Experience());

        sendAttributes(m);

        return "redirect:/admin#experience";
    }

    @PostMapping( value="/admin", params="submitEdu")
    public String updateEdu(@ModelAttribute EducationWrapper eduWrapper, Model m) {

        educationDAO.saveAll(eduWrapper.getEducationList());

        sendAttributes(m);

        return "redirect:/admin#education";
    }

    @PostMapping( value="/admin", params="removeEdu")
    public String deleteEdu(@RequestParam Long eduId, Model m) {

        educationDAO.deleteById(eduId);

        sendAttributes(m);

        return "redirect:/admin#education";
    }

    @PostMapping( value="/admin", params="addEdu")
    public String addEdu(Model m) {

        educationDAO.save(new Education());

        sendAttributes(m);

        return "redirect:/admin#education";
    }


    public List<Experience> sortExperiences() {
        List<Experience> experiences = new ArrayList<>();

        experienceDAO.findAll().forEach(experiences::add);

        Collections.sort(experiences);
        return experiences;
    }

    public List<Education> sortEducations() {
        List<Education> educations = new ArrayList<>();

        educationDAO.findAll().forEach(educations::add);

        Collections.sort(educations);
        return educations;
    }



    @PostMapping("/contact")
    public RedirectView contactAdmin(RedirectAttributes attrs, @ModelAttribute Mail mail) throws MessagingException {


        // à rajouter dans la fct pour passer en post @ModelAttribute MailObject mailObject,
        // TODO : ajouter ancre navbar de gauche dans index pour relier à contact
        // TODO : regarder ajax pour ne pas reload la page
        // TODO : supprimer download.html ?

        sendMail(mail);
        return new RedirectView("/");
    }

    private void sendMail(Mail mail) throws MessagingException {
        mail.setRecipientName("thymeleaf");
        mail.setSubject("Sujet");
        // TODO : remove  after tests mail.setTo("remi.beltramini@gmail.com");
        mail.setTo(getCurrentUser().getEmail());
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", mail.getRecipientName());
        templateModel.put("text", mail.getText());
        templateModel.put("senderName", mail.getSenderName());
        emailService.sendMessageUsingThymeleafTemplate(mail.getTo(), mail.getSubject(), templateModel);
    }

    public User getCurrentUser(){
        // TODO : ajouter tests , erreurs etc ...
        return userDAO.findById((long) 1).get();
    }


}
