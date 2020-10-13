package io.takima.demo;

import io.takima.demo.DAO.UserDAO;
import org.jetbrains.annotations.NotNull;
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

    public LibraryController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public String homePage(Model m) {

        Optional<User> optUser = userDAO.findById((long) 1);
        if(optUser.isPresent()) {
            m.addAttribute("user",optUser.get());
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
