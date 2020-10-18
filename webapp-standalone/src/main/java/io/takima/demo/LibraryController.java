package io.takima.demo;
import io.takima.demo.DAO.*;
import io.takima.demo.mail.EmailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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
        Optional<Presentation> optPresentation = presentationDAO.findById((long) 1);

        //TODO: add function

        if(optUser.isPresent() && optPresentation.isPresent()) {

            m.addAttribute("user",getCurrentUser());
            m.addAttribute("hobbies", hobbyDAO.findAll());
            m.addAttribute("education", educationDAO.findAll());
            m.addAttribute("skill", skillDAO.findAll());
            m.addAttribute("project", projectDAO.findAll());
            m.addAttribute("experience", experienceDAO.findAll());
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
        Optional<User> optUser = userDAO.findById((long) 1);
        Optional<Presentation> optPresentation = presentationDAO.findById((long) 1);

        //TODO: add function

        if(optUser.isPresent() && optPresentation.isPresent()) {

            m.addAttribute("user",optUser.get());
            m.addAttribute("hobbies", hobbyDAO.findAll());
            m.addAttribute("education", educationDAO.findAll());
            //         m.addAttribute("skill", skillDAO.findAll());
            m.addAttribute("project", projectDAO.findAll());
            m.addAttribute("experience", experienceDAO.findAll());
            m.addAttribute("presentation", optPresentation.get());

            return "admin";
        }
        else{
            return "404";
        }
    }

    @PostMapping(value = "/admin")
    public String updateUser(@ModelAttribute User user, @ModelAttribute Presentation presentation, Model m) {

        user.setId((long) 1);

        userDAO.save(user);

        presentation.setId((long) 1);

        presentationDAO.save(presentation);

        m.addAttribute("user", user);
        m.addAttribute("hobbies", hobbyDAO.findAll());
        m.addAttribute("education", educationDAO.findAll());
        //         m.addAttribute("skill", skillDAO.findAll());
        m.addAttribute("project", projectDAO.findAll());
        m.addAttribute("experience", experienceDAO.findAll());
        m.addAttribute("presentation", presentation);

        return "admin";
    }

    /*@RequestMapping(value="/admin", params={"addRow"})
    public String addRow(final Experience experience, final BindingResult bindingResult) {
        Experience exp = new Experience();
        experienceDAO.save(exp);

        return "admin";
    }

    @RequestMapping(value="/admin", params={"removeRow"})
    public String removeRow(
            final Experience experience, final BindingResult bindingResult,
            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        experienceDAO.delete(experience);
        return "admin";
    }*/



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
