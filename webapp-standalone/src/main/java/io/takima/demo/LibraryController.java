package io.takima.demo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import io.takima.demo.Classes.*;
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
import java.util.concurrent.ExecutionException;

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
    private ArrayList<Skill> skillList;
    private ArrayList<Hobby> hobbyList;
    private ArrayList<Project> projectList;

    public LibraryController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, EmailServiceImpl emailService, ArrayList<Experience> experienceList, ArrayList<Education> educationList, ArrayList<Skill> skillList, ArrayList<Hobby> hobbyList, ArrayList<Project> projectList) {
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
        this.skillList = skillList;
        this.hobbyList = hobbyList;
        this.projectList = projectList;
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

    @GetMapping("/login")
    public String loginPage(Model m) {

            return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String inputEmail, @RequestParam String inputPassword) throws Exception {

        String token = FireAuth.getInstance().auth(inputEmail, inputPassword);
        System.out.println( FirebaseAuth.getInstance().verifyIdToken(token));


        return "login";
    }

    @GetMapping("/admin")
    public String addUserPage(Model m) {

        sendAttributesAdmin(m);

        return "admin";
    }

    private void sendAttributesAdmin(Model m) {
        Optional<User> optUser = userDAO.findById((long) 1);
        Optional<Presentation> optPresentation = presentationDAO.findById((long) 1);

        //TODO: add function

        if(optUser.isPresent() && optPresentation.isPresent()) {

            m.addAttribute("user", optUser.get());
            m.addAttribute("presentation", optPresentation.get());
            m.addAttribute("expWrapper", getExperienceWrapper());
            m.addAttribute("eduWrapper", getEducationWrapper());
            m.addAttribute("skillWrapper", getSkillWrapper());
            m.addAttribute("hobbyWrapper", getHobbyWrapper());
            m.addAttribute("projectWrapper", getProjectWrapper());

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

    @PostMapping( value="/admin", params="submitUser")
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

        FireService top = new FireService();
        top.savePatientDetails(user);

        return "redirect:/admin#about";
    }

    @PostMapping( value="/admin", params="submitExp")
    public String updateExp(@ModelAttribute ExperienceWrapper expWrapper, Model m) {

        experienceDAO.saveAll(expWrapper.getExperienceList());

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    @PostMapping( value="/admin", params="removeExp")
    public String deleteExp(@RequestParam Long expId, Model m) {

        experienceDAO.deleteById(expId);

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    @PostMapping( value="/admin", params="addExp")
    public String addExp(Model m) {

        experienceDAO.save(new Experience());

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    @PostMapping( value="/admin", params="submitEdu")
    public String updateEdu(@ModelAttribute EducationWrapper eduWrapper, Model m) {

        educationDAO.saveAll(eduWrapper.getEducationList());

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    @PostMapping( value="/admin", params="removeEdu")
    public String deleteEdu(@RequestParam Long eduId, Model m) {

        educationDAO.deleteById(eduId);

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    @PostMapping( value="/admin", params="addEdu")
    public String addEdu(Model m) {

        educationDAO.save(new Education());

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    @PostMapping( value="/admin", params="submitSkill")
    public String updateSkill(@ModelAttribute SkillWrapper skillWrapper, Model m) {

        skillDAO.saveAll(skillWrapper.getSkillList());

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    @PostMapping( value="/admin", params="removeSkill")
    public String deleteSkill(@RequestParam Long skillId, Model m) {

        skillDAO.deleteById(skillId);

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    @PostMapping( value="/admin", params="addSkill")
    public String addSkill(Model m) {

        skillDAO.save(new Skill());

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }
    
    @PostMapping( value="/admin", params="submitHobby")
    public String updateHobby(@ModelAttribute HobbyWrapper HobbyWrapper, Model m) {

        hobbyDAO.saveAll(HobbyWrapper.getHobbyList());

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    @PostMapping( value="/admin", params="removeHobby")
    public String deleteHobby(@RequestParam Long hobbyId, Model m) {

        hobbyDAO.deleteById(hobbyId);

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    @PostMapping( value="/admin", params="addHobby")
    public String addHobby(Model m) {

        hobbyDAO.save(new Hobby());

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    @PostMapping( value="/admin", params="submitProject")
    public String updateProject(@ModelAttribute ProjectWrapper ProjectWrapper, Model m) {

        projectDAO.saveAll(ProjectWrapper.getProjectList());

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    @PostMapping( value="/admin", params="removeProject")
    public String deleteProject(@RequestParam Long projectId, Model m) {

        projectDAO.deleteById(projectId);

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    @PostMapping( value="/admin", params="addProject")
    public String addProject(Model m) {

        projectDAO.save(new Project());

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
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


        // à rajouter dans la fct pour passer en post @ModelAttribute MailObject mailObject,
        // TODO : ajouter ancre navbar de gauche dans index pour relier à contact
        // TODO : regarder ajax pour ne pas reload la page
        // TODO : supprimer download.html ?

        sendMail(mail);
        return new RedirectView("/");
    }

    private void sendMail(Mail mail) throws MessagingException {
        //mail.setRecipientName("thymeleaf");
        //mail.setSubject("Sujet");
        // TODO : remove  after tests mail.setTo("remi.beltramini@gmail.com");
        mail.setTo("clement.bardoux@epfedu.fr");
        Map<String, Object> templateModel = new HashMap<>();
        //templateModel.put("recipientName", mail.getRecipientName());
        templateModel.put("senderName", mail.getSenderName());
        templateModel.put("senderMail", mail.getSenderMail());
        templateModel.put("subject", mail.getSubject());
        templateModel.put("text", mail.getText());
        templateModel.put("receiverName", getCurrentUser().getLastName());
        emailService.sendMessageUsingThymeleafTemplate(mail.getTo(), mail.getSubject(), templateModel);
    }

    public User getCurrentUser(){
        // TODO : ajouter tests , erreurs etc ...
        return userDAO.findById((long) 1).get();
    }


}
