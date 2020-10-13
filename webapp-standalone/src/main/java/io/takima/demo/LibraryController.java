package io.takima.demo;

import io.takima.demo.DAO.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Optional;

/**
 *
 */
@RequestMapping("/")
@Controller
public class LibraryController {

    private final UserDAO userDAO;
    private final HobbiesDAO hobbiesDao;
    private final EducationDAO educationDao;
    private final SkillDAO  skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;



    public LibraryController(UserDAO userDAO, HobbiesDAO hobbiesDao, EducationDAO educationDao, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO) {
        this.userDAO = userDAO;
        this.hobbiesDao = hobbiesDao;
        this.educationDao = educationDao;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
    }

    @GetMapping
    public String homePage(Model m) {

        Optional<User> optUser = userDAO.findById((long) 1);

        if(optUser.isPresent()) {

            m.addAttribute("user",optUser.get());
            m.addAttribute("hobbies", hobbiesDao.findAll());
            m.addAttribute("education", educationDao.findAll());
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
        userDAO.save(user);
        return new RedirectView("/");
    }
}
