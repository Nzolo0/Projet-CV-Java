package io.takima.demo;

import io.takima.demo.classes.*;
import io.takima.demo.classes.file.ResponseFile;
import io.takima.demo.dao.*;
import io.takima.demo.files.FileStorageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@RequestMapping("/")
@Controller
public class IndexController {

    private final UserDAO userDAO;
    private final HobbyDAO hobbyDAO;
    private final EducationDAO educationDAO;
    private final SkillDAO skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;
    private final FileStorageService fileStorageService;

    public IndexController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, FileStorageService fileStorageService) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public String homePage(Model m) {

        sendAttributesIndex(m);

        return "index";
    }

    private void sendAttributesIndex(Model m) {

        List<ResponseFile> files = collectFilesUrl();

        Profile profile = getProfile();

        if (files.stream().findFirst().isPresent()) {
            m.addAttribute("files", files.stream().findFirst().get());
        }

        m.addAttribute("user", profile.getCurrentUserHTML());
        m.addAttribute("hobbies", profile.getHobbyHTML());
        m.addAttribute("education", profile.getEducationHTML()); // add sort
        m.addAttribute("skill", profile.getSkillHTML());
        m.addAttribute("project", profile.getProject());
        m.addAttribute("experience",profile.getExperienceHTML());
        m.addAttribute("presentation", profile.getPresentationHTML());
        m.addAttribute("mail", new Mail());
    }

    @NotNull
    private List<ResponseFile> collectFilesUrl() {
        return fileStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
    }


    @PostMapping(value = "/", params = "signIn")
    public String signInIndex() {

        return "redirect:/login";
    }

    @PostMapping(value = "/", params = "signOut")
    public String signOutIndex(@RequestParam String tok) {

        AdminController.token = tok;

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



}

