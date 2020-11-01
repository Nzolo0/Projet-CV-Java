package io.takima.demo;

import io.takima.demo.Classes.*;
import io.takima.demo.DAO.*;
import io.takima.demo.files.FileStorageService;
import io.takima.demo.firebase.FireAuth;
import io.takima.demo.mail.EmailServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.mail.MessagingException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@RequestMapping("/")
@Controller
public class LibraryController {

    private final UserDAO userDAO;
    private final HobbyDAO hobbyDAO;
    private final EducationDAO educationDAO;
    private final SkillDAO skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;
    private final FileStorageService fileStorageService;
    private final EmailServiceImpl emailService;

    private ArrayList<Experience> experienceList;
    private ArrayList<Education> educationList;
    private ArrayList<Skill> skillList;
    private ArrayList<Hobby> hobbyList;
    private ArrayList<Project> projectList;

    private String token = null;

    public LibraryController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, FileStorageService fileStorageService, EmailServiceImpl emailService, ArrayList<Experience> experienceList, ArrayList<Education> educationList, ArrayList<Skill> skillList, ArrayList<Hobby> hobbyList, ArrayList<Project> projectList) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
        this.fileStorageService = fileStorageService;
        this.emailService = emailService;
        this.experienceList = experienceList;
        this.educationList = educationList;
        this.skillList = skillList;
        this.hobbyList = hobbyList;
        this.projectList = projectList;
    }

    @GetMapping
    public String homePage(Model m) {

        sendAttributesIndex(m);

        return "index";
    }

    private void sendAttributesIndex(Model m) {

        // TODO : change it
        // Optional<User> optUser = userDAO.findById((long) 1);
        Optional<Presentation> optPresentation = Optional.ofNullable(presentationDAO.findAll().iterator().next());

        List<ResponseFile> files = collectFilesUrl();

        Profile profile = getProfile();


        if (true && optPresentation.isPresent() && files.stream().findFirst().isPresent()) {

            m.addAttribute("user", profile.getCurrentUserHTML());
            m.addAttribute("hobbies", profile.getHobbyHTML());
            m.addAttribute("education", profile.getEducationHTML()); // add sort
            m.addAttribute("skill", profile.getSkillHTML());
            m.addAttribute("project", profile.getProject());
            m.addAttribute("experience",profile.getExperienceHTML());
            m.addAttribute("presentation", profile.getPresentationHTML());
            m.addAttribute("mail", new Mail());
            m.addAttribute("files", files.stream().findFirst().get());

        }
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

    @GetMapping("/login")
    public String loginPage(Model m) {

        List<ResponseFile> files = collectFilesUrl();

        if (files.stream().findFirst().isPresent()) {

            m.addAttribute("files", files.stream().findFirst().get());
        }

        if (token == null || token.trim().isEmpty()) {
            return "login";
        } else {

            return "redirect:/";
        }
    }

    @GetMapping("/test")
    public String testPage() {
        return "test";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String inputEmail, @RequestParam String inputPassword) throws Exception {

        token = FireAuth.getInstance().auth(inputEmail, inputPassword);

        if (token == null || token.trim().isEmpty()) {
            return "login";
        } else {

            return "redirect:/admin";
        }
    }

    @GetMapping("/admin")
    public String addUserPage(Model m) {

        if (token == null || token.trim().isEmpty()) {
            return "redirect:/login";
        } else {
            sendAttributesAdmin(m);

            return "admin";
        }
    }

    private void sendAttributesAdmin(Model m) {
        // todo: change it
        //Optional<User> optUser = userDAO.findById((long) 1);
        Optional<Presentation> optPresentation = Optional.ofNullable(presentationDAO.findAll().iterator().next());

        List<ResponseFile> files = collectFilesUrl();

        //if(true && optPresentation.isPresent() && files.stream().findFirst().isPresent()) {
        if (true && true && files.stream().findFirst().isPresent()) {
            
            m.addAttribute("user", getCurrentUser());
            m.addAttribute("presentation", optPresentation.get());
            m.addAttribute("expWrapper", getExperienceWrapper());
            m.addAttribute("eduWrapper", getEducationWrapper());
            m.addAttribute("skillWrapper", getSkillWrapper());
            m.addAttribute("hobbyWrapper", getHobbyWrapper());
            m.addAttribute("projectWrapper", getProjectWrapper());
            m.addAttribute("files", files.stream().findFirst().get());

        }
    }

    @PostMapping(value = "/", params = "signIn")
    public String signInIndex() {

        return "redirect:/login";
    }

    @PostMapping(value = "/", params = "signOut")
    public String signOutIndex(@RequestParam String tok) {

        token = tok;
        System.out.println(tok);

        return "redirect:/";

    }

    @PostMapping(value = "/admin", params = "signOut")
    public String signOutAdmin(@RequestParam String tok) {

        token = tok;
        System.out.println(tok);

        return "redirect:/";
    }


    @PostMapping(value = "/admin", params = "submitUser")
    public String updateUser(@ModelAttribute User user, @ModelAttribute Presentation presentation, Model m) throws ExecutionException, InterruptedException {

        user.setId((long) 1);

        userDAO.save(user);

        presentation.setId((long) 1);

        presentationDAO.save(presentation);

        m.addAttribute("user", user);
        m.addAttribute("presentation", presentation);
        m.addAttribute("expWrapper", getExperienceWrapper());
        m.addAttribute("eduWrapper", getEducationWrapper());
        m.addAttribute("skillWrapper", getSkillWrapper());
        m.addAttribute("hobbyWrapper", getHobbyWrapper());
        m.addAttribute("projectWrapper", getProjectWrapper());

        return "redirect:/admin#about";
    }

    @PostMapping(value = "/admin", params = "submitExp")
    public String updateExp(@ModelAttribute ExperienceWrapper expWrapper, Model m) {

        experienceDAO.saveAll(expWrapper.getExperienceList());

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    @PostMapping(value = "/admin", params = "removeExp")
    public String deleteExp(@RequestParam Long expId, Model m) {

        experienceDAO.deleteById(expId);

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    @PostMapping(value = "/admin", params = "addExp")
    public String addExp(Model m) {

        experienceDAO.save(new Experience());

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    @PostMapping(value = "/admin", params = "submitEdu")
    public String updateEdu(@ModelAttribute EducationWrapper eduWrapper, Model m) {

        educationDAO.saveAll(eduWrapper.getEducationList());

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    @PostMapping(value = "/admin", params = "removeEdu")
    public String deleteEdu(@RequestParam Long eduId, Model m) {

        educationDAO.deleteById(eduId);

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    @PostMapping(value = "/admin", params = "addEdu")
    public String addEdu(Model m) {

        educationDAO.save(new Education());

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    @PostMapping(value = "/admin", params = "submitSkill")
    public String updateSkill(@ModelAttribute SkillWrapper skillWrapper, Model m) {

        skillDAO.saveAll(skillWrapper.getSkillList());

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    @PostMapping(value = "/admin", params = "removeSkill")
    public String deleteSkill(@RequestParam Long skillId, Model m) {

        skillDAO.deleteById(skillId);

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    @PostMapping(value = "/admin", params = "addSkill")
    public String addSkill(Model m) {

        skillDAO.save(new Skill());

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    @PostMapping(value = "/admin", params = "submitHobby")
    public String updateHobby(@ModelAttribute HobbyWrapper HobbyWrapper, Model m) {

        hobbyDAO.saveAll(HobbyWrapper.getHobbyList());

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    @PostMapping(value = "/admin", params = "removeHobby")
    public String deleteHobby(@RequestParam Long hobbyId, Model m) {

        hobbyDAO.deleteById(hobbyId);

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    @PostMapping(value = "/admin", params = "addHobby")
    public String addHobby(Model m) {

        hobbyDAO.save(new Hobby());

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    @PostMapping(value = "/admin", params = "submitProject")
    public String updateProject(@ModelAttribute ProjectWrapper ProjectWrapper, Model m) {

        projectDAO.saveAll(ProjectWrapper.getProjectList());

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    @PostMapping(value = "/admin", params = "removeProject")
    public String deleteProject(@RequestParam Long projectId, Model m) {

        projectDAO.deleteById(projectId);

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    @PostMapping(value = "/admin", params = "addProject")
    public String addProject(Model m) {

        projectDAO.save(new Project());

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
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

    @NotNull
    private SkillWrapper getSkillWrapper() {
        skillList = (ArrayList<Skill>) sortSkills();
        SkillWrapper skillWrapper = new SkillWrapper();
        skillWrapper.setSkillList(skillList);
        return skillWrapper;
    }

    @NotNull
    private HobbyWrapper getHobbyWrapper() {
        hobbyList = (ArrayList<Hobby>) sortHobbies();
        HobbyWrapper hobbyWrapper = new HobbyWrapper();
        hobbyWrapper.setHobbyList(hobbyList);
        return hobbyWrapper;
    }

    @NotNull
    private ProjectWrapper getProjectWrapper() {
        projectList = (ArrayList<Project>) sortProjects();
        ProjectWrapper projectWrapper = new ProjectWrapper();
        projectWrapper.setProjectList(projectList);
        return projectWrapper;
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

    public List<Skill> sortSkills() {
        List<Skill> skills = new ArrayList<>();

        skillDAO.findAll().forEach(skills::add);

        Collections.sort(skills);
        return skills;
    }

    public List<Hobby> sortHobbies() {
        List<Hobby> hobbies = new ArrayList<>();

        hobbyDAO.findAll().forEach(hobbies::add);

        Collections.sort(hobbies);
        return hobbies;
    }

    public List<Project> sortProjects() {
        List<Project> projects = new ArrayList<>();

        projectDAO.findAll().forEach(projects::add);

        Collections.sort(projects);
        return projects;
    }


    @PostMapping("/contact")
    public RedirectView contactAdmin(RedirectAttributes attrs, @ModelAttribute Mail mail) throws MessagingException {
        sendMail(mail);
        return new RedirectView("/");
    }

    private void sendMail(Mail mail) throws MessagingException {
        //mail.setRecipientName("thymeleaf");
        //mail.setSubject("Sujet");
        // TODO : remove  after tests mail.setTo("JeanLapin ?);
        mail.setTo("clement.bardoux@epfedu.fr");
        Map<String, Object> templateModel = new HashMap<>();

        templateModel.put("senderName", mail.getSenderName());
        templateModel.put("senderMail", mail.getSenderMail());
        templateModel.put("subject", mail.getSubject());
        templateModel.put("text", mail.getText());
        templateModel.put("receiverName", getCurrentUser().getLastName());
        emailService.sendMessageUsingThymeleafTemplate(mail.getTo(), mail.getSubject(), templateModel);
    }

    public User getCurrentUser() {
        // TODO : ajouter tests , erreurs etc ...
        User user = userDAO.findAll().iterator().next();
        System.out.println(user.toString());
        return user;
    }

    public User getCurrentUserHTML() {
        // TODO : ajouter tests , erreurs etc ...
        User user = userDAO.findAll().iterator().next();
        System.out.println(user.toString());

        user.setLastName(markdownToHTML(user.getLastName()));
        System.out.println(user.getLastName());
        user.setEmail(markdownToHTML(user.getEmail()));
        user.setAddress(markdownToHTML(user.getAddress()));
        return user;
    }

    @GetMapping("/uploadP")
    public String UploadPage(Model m) {
        return "upload";
    }


    private String markdownToHTML(String markdown) {
        Parser parser = Parser.builder()
                .build();

        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .build();

        return renderer.render(document).replace("<p>", "").replace("</p>", "");
    }

    private User getUserHTML(User user) {
        return new User(
                user.getId(),
                markdownToHTML(user.getFirstName()),
                markdownToHTML(user.getLastName()),
                user.getAge(),
                markdownToHTML(user.getPhone()),
                markdownToHTML(user.getEmail()),
                markdownToHTML(user.getAddress()),
                markdownToHTML(user.getTitle()),
                markdownToHTML(user.getLinkedin()),
                markdownToHTML(user.getGithub()),
                markdownToHTML(user.getInstagram()),
                markdownToHTML(user.getFacebook()));

    }

    private Iterable<Project> getProjectHTML(ArrayList<Project> project) {

        ArrayList<Project> projectHtml = new ArrayList<Project>();

        for (Project item : project) {
       //     projectHtml.add(new Hobby(item.getId(),markdownToHTML(item.getTitle()),markdownToHTML(item.getDetails())));
        }

        return projectHtml;

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
