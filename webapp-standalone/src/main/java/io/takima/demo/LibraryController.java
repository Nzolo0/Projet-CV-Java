package io.takima.demo;
import io.takima.demo.Classes.MailObject;
import io.takima.demo.DAO.*;
import io.takima.demo.mail.EmailService;
import io.takima.demo.mail.EmailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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



    public LibraryController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, EmailServiceImpl emailService) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
        this.emailService = emailService;
    }

    @GetMapping
    public String homePage(Model m) {

        Optional<User> optUser = userDAO.findById((long) 1);

            //TODO: add function

        if(optUser.isPresent()) {

            m.addAttribute("user",optUser.get());
            m.addAttribute("hobbies", hobbyDAO.findAll());
            m.addAttribute("education", educationDAO.findAll());
            m.addAttribute("skill", skillDAO.findAll());
            m.addAttribute("project", projectDAO.findAll());
            m.addAttribute("experience", experienceDAO.findAll());
            m.addAttribute("presentation", presentationDAO.findAll());

            return "index";
        }
        else{
            return "404";
        }
    }

    @GetMapping("/admin")
    public String addUserPage(Model m) {
        m.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/admin")
    public RedirectView createNewUser(@ModelAttribute User user, RedirectAttributes attrs) {
        attrs.addFlashAttribute("message", "Utilisateur ajouté avec succès");
      //  userDAO.save(user);
        return new RedirectView("/");
    }

    @GetMapping("/contact")
    public RedirectView contactAdmin(RedirectAttributes attrs) throws MessagingException {


        // à rajouter dans la fct pour passer en post @ModelAttribute MailObject mailObject,
        // TODO : tout mettre dans une fonction
        // TODO : ajouter formulaire dans index
        // TODO : ajouter ancre navbar de gauche dans index pour relier à contact
        // TODO : regarder ajax pour ne pas reload la page
        // TODO : supprimer download.html ?
        MailObject mailObject2 = new MailObject();
        mailObject2.setRecipientName("thymeleaf");
        mailObject2.setSubject("SUejt");
        mailObject2.setSenderName("renizmy");
        mailObject2.setTo("remi.beltramini@gmail.com");
        mailObject2.setTemplateEngine("thymeleaf");

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", mailObject2.getRecipientName());
        templateModel.put("text", mailObject2.getText());
        templateModel.put("senderName", mailObject2.getSenderName());


        emailService.sendMessageUsingThymeleafTemplate(mailObject2.getTo(), mailObject2.getSubject(), templateModel);


        attrs.addFlashAttribute("message", "Mail envoyé ! ");


        return new RedirectView("/");
    }


}
