package io.takima.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.takima.demo.classes.file.ResponseFile;
import io.takima.demo.classes.wrapper.*;
import io.takima.demo.dao.*;
import io.takima.demo.files.FileStorageService;
import io.takima.demo.firebase.FireAuth;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Controller for admin page
 */
@RequestMapping("/")
@Controller
public class AdminController {

    public static String token = null;
    private final UserDAO userDAO;
    private final HobbyDAO hobbyDAO;
    private final EducationDAO educationDAO;
    private final SkillDAO skillDAO;
    private final ProjectDAO projectDAO;
    private final ExperienceDAO experienceDAO;
    private final PresentationDAO presentationDAO;
    private final FileStorageService fileStorageService;
    public String clientId = "77d3miszbwaz49";
    public String clientSecret = "jKNQuZsVuSXnhg1i";
    public String redirectUrl = "http://localhost:8080/afterauth";
    private ArrayList<Experience> experienceList;
    private ArrayList<Education> educationList;
    private ArrayList<Skill> skillList;
    private ArrayList<Hobby> hobbyList;
    private ArrayList<Project> projectList;

    public AdminController(UserDAO userDAO, HobbyDAO hobbyDAO, EducationDAO educationDAO, SkillDAO skillDAO, ProjectDAO projectDAO, ExperienceDAO experienceDAO, PresentationDAO presentationDAO, FileStorageService fileStorageService, ArrayList<Experience> experienceList, ArrayList<Education> educationList, ArrayList<Skill> skillList, ArrayList<Hobby> hobbyList, ArrayList<Project> projectList) {
        this.userDAO = userDAO;
        this.hobbyDAO = hobbyDAO;
        this.educationDAO = educationDAO;
        this.skillDAO = skillDAO;
        this.projectDAO = projectDAO;
        this.experienceDAO = experienceDAO;
        this.presentationDAO = presentationDAO;
        this.fileStorageService = fileStorageService;
        this.experienceList = experienceList;
        this.educationList = educationList;
        this.skillList = skillList;
        this.hobbyList = hobbyList;
        this.projectList = projectList;
    }

    /**
     * Method to access login page
     * @param m Model
     * @return index page if connected, login page if not connected
     */
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

    /**
     * Method to login to the website
     * @param inputEmail User's email
     * @param inputPassword User's password
     * @return admin page if connected, login page if not connected
     * @throws Exception Error
     */
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String inputEmail, @RequestParam String inputPassword) throws Exception {

        token = FireAuth.getInstance().auth(inputEmail, inputPassword);

        if (token == null || token.trim().isEmpty()) {
            return "login";
        } else {

            return "redirect:/admin";
        }
    }

    /**
     * Method to access admin page
     * @param m Model
     * @return admin page if connected, login page if not connected
     */
    @GetMapping("/admin")
    public String addUserPage(Model m) {

        if (token == null || token.trim().isEmpty()) {
            return "redirect:/login";
        } else {
            sendAttributesAdmin(m);

            return "admin";
        }
    }

    /**
     * Extracted method to send the attributes
     * @param m Model
     */
    private void sendAttributesAdmin(Model m) {

        Optional<Presentation> optPresentation = Optional.ofNullable(presentationDAO.findAll().iterator().next());

        List<ResponseFile> files = collectFilesUrl();

        //if(true && optPresentation.isPresent() && files.stream().findFirst().isPresent()) {
        if (files.stream().findFirst().isPresent()) {
            m.addAttribute("files", files.stream().findFirst().get());
        }

        m.addAttribute("user", getCurrentUser());
        m.addAttribute("presentation", optPresentation.get());
        m.addAttribute("expWrapper", getExperienceWrapper());
        m.addAttribute("eduWrapper", getEducationWrapper());
        m.addAttribute("skillWrapper", getSkillWrapper());
        m.addAttribute("hobbyWrapper", getHobbyWrapper());
        m.addAttribute("projectWrapper", getProjectWrapper());
    }

    /**
     * Method to sign out the user
     * @param tok User's token
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "signOut")
    public String signOutAdmin(@RequestParam String tok) {

        token = tok;

        return "redirect:/";
    }

    /**
     * Update about and presentation data
     * @param user User
     * @param presentation Presentation
     * @param m Model
     * @return admin page
     * @throws ExecutionException Error
     * @throws InterruptedException Error
     */
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

    /**
     * Update experience data
     * @param expWrapper Experiences list
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "submitExp")
    public String updateExp(@ModelAttribute ExperienceWrapper expWrapper, Model m) {

        experienceDAO.saveAll(expWrapper.getExperienceList());

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    /**
     * Delete experience
     * @param expId Experience's id
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "removeExp")
    public String deleteExp(@RequestParam Long expId, Model m) {

        experienceDAO.deleteById(expId);

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    /**
     * Add experience
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "addExp")
    public String addExp(Model m) {

        experienceDAO.save(new Experience());

        sendAttributesAdmin(m);

        return "redirect:/admin#experience";
    }

    /**
     * Update education data
     * @param eduWrapper Educations list
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "submitEdu")
    public String updateEdu(@ModelAttribute EducationWrapper eduWrapper, Model m) {

        educationDAO.saveAll(eduWrapper.getEducationList());

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    /**
     * Delete education
     * @param eduId Education's id
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "removeEdu")
    public String deleteEdu(@RequestParam Long eduId, Model m) {

        educationDAO.deleteById(eduId);

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    /**
     * Add education
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "addEdu")
    public String addEdu(Model m) {

        educationDAO.save(new Education());

        sendAttributesAdmin(m);

        return "redirect:/admin#education";
    }

    /**
     * Update skills
     * @param skillWrapper Skills list
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "submitSkill")
    public String updateSkill(@ModelAttribute SkillWrapper skillWrapper, Model m) {

        skillDAO.saveAll(skillWrapper.getSkillList());

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    /**
     * Delete skill
     * @param skillId Skill's id
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "removeSkill")
    public String deleteSkill(@RequestParam Long skillId, Model m) {

        skillDAO.deleteById(skillId);

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    /**
     * Add skill
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "addSkill")
    public String addSkill(Model m) {

        skillDAO.save(new Skill());

        sendAttributesAdmin(m);

        return "redirect:/admin#skills";
    }

    /**
     * Update Hobbies
     * @param HobbyWrapper Hobbies list
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "submitHobby")
    public String updateHobby(@ModelAttribute HobbyWrapper HobbyWrapper, Model m) {

        hobbyDAO.saveAll(HobbyWrapper.getHobbyList());

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    /**
     * Delete hobby
     * @param hobbyId Hobby's id
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "removeHobby")
    public String deleteHobby(@RequestParam Long hobbyId, Model m) {

        hobbyDAO.deleteById(hobbyId);

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    /**
     * Add hobby
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "addHobby")
    public String addHobby(Model m) {

        hobbyDAO.save(new Hobby());

        sendAttributesAdmin(m);

        return "redirect:/admin#hobbies";
    }

    /**
     * Update projects
     * @param ProjectWrapper Projects list
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "submitProject")
    public String updateProject(@ModelAttribute ProjectWrapper ProjectWrapper, Model m) {

        projectDAO.saveAll(ProjectWrapper.getProjectList());

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    /**
     * Delete project
     * @param projectId Project's id
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "removeProject")
    public String deleteProject(@RequestParam Long projectId, Model m) {

        projectDAO.deleteById(projectId);

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    /**
     * Add project
     * @param m Model
     * @return admin page
     */
    @PostMapping(value = "/admin", params = "addProject")
    public String addProject(Model m) {

        projectDAO.save(new Project());

        sendAttributesAdmin(m);

        return "redirect:/admin#projects";
    }

    /**
     * Extracted method to get the profile images urls
     * @return List of profile images
     */
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

    /**
     * Wrapper for the experiences
     * @return Experiences list
     */
    @NotNull
    private ExperienceWrapper getExperienceWrapper() {
        experienceList = (ArrayList<Experience>) sortExperiences();
        ExperienceWrapper expWrapper = new ExperienceWrapper();
        expWrapper.setExperienceList(experienceList);
        return expWrapper;
    }

    /**
     * Wrapper for the educations
     * @return Educations list
     */
    @NotNull
    private EducationWrapper getEducationWrapper() {
        educationList = (ArrayList<Education>) sortEducations();
        EducationWrapper eduWrapper = new EducationWrapper();
        eduWrapper.setEducationList(educationList);
        return eduWrapper;
    }

    /**
     * Wrapper for the skills
     * @return Skills list
     */
    @NotNull
    private SkillWrapper getSkillWrapper() {
        skillList = (ArrayList<Skill>) sortSkills();
        SkillWrapper skillWrapper = new SkillWrapper();
        skillWrapper.setSkillList(skillList);
        return skillWrapper;
    }

    /**
     * Wrapper for the hobbies
     * @return Hobbies list
     */
    @NotNull
    private HobbyWrapper getHobbyWrapper() {
        hobbyList = (ArrayList<Hobby>) sortHobbies();
        HobbyWrapper hobbyWrapper = new HobbyWrapper();
        hobbyWrapper.setHobbyList(hobbyList);
        return hobbyWrapper;
    }

    /**
     * Wrapper for the projects
     * @return Projects list
     */
    @NotNull
    private ProjectWrapper getProjectWrapper() {
        projectList = (ArrayList<Project>) sortProjects();
        ProjectWrapper projectWrapper = new ProjectWrapper();
        projectWrapper.setProjectList(projectList);
        return projectWrapper;
    }

    /**
     * Sort experiences by date
     * @return Experiences list
     */
    public List<Experience> sortExperiences() {
        List<Experience> experiences = new ArrayList<>();

        experienceDAO.findAll().forEach(experiences::add);

        Collections.sort(experiences);
        return experiences;
    }

    /**
     * Sort educations by date
     * @return Educations list
     */
    public List<Education> sortEducations() {
        List<Education> educations = new ArrayList<>();

        educationDAO.findAll().forEach(educations::add);

        Collections.sort(educations);
        return educations;
    }

    /**
     * Sort skills by alphabetical order
     * @return Skills list
     */
    public List<Skill> sortSkills() {
        List<Skill> skills = new ArrayList<>();

        skillDAO.findAll().forEach(skills::add);

        Collections.sort(skills);
        return skills;
    }

    /**
     * Sort hobbies by alphabetical
     * @return Hobbies list
     */
    public List<Hobby> sortHobbies() {
        List<Hobby> hobbies = new ArrayList<>();

        hobbyDAO.findAll().forEach(hobbies::add);

        Collections.sort(hobbies);
        return hobbies;
    }

    /**
     * Sort projects by date
     * @return Projects list
     */
    public List<Project> sortProjects() {
        List<Project> projects = new ArrayList<>();

        projectDAO.findAll().forEach(projects::add);

        Collections.sort(projects);
        return projects;
    }

    /**
     * Extract User's Profile
     * @return Profile
     */
    public User getCurrentUser() {
        return userDAO.findAll().iterator().next();
    }

    /**
     * Create button on the page and hit this get request
     * @return linkedin connection page
     */
    @GetMapping(value = "/authorization", params = "testAuth")
    public String authorization() {
        String authorizationUri = "https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&scope=r_liteprofile%20r_emailaddress";
        return "redirect:" + authorizationUri;
    }

    /**
     * After login in your linkedin account your app will hit this get request
     * @param authorizationCode Authorization code
     * @return admin page
     * @throws JSONException Error
     * @throws JsonProcessingException Error
     */
    @GetMapping("/afterauth")
    //now store your authorization code
    public String afterauth(@RequestParam("code") String authorizationCode) throws JSONException, JsonProcessingException {

        //to trade your authorization code for access token
        String accessTokenUri = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + redirectUrl + "&client_id=" + clientId + "&client_secret=" + clientSecret + "";

        // linkedin api to get linkedidn profile detail
        String linedkinDetailUri = "https://api.linkedin.com/v2/me";

        //store your access token
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenRequest = restTemplate.getForObject(accessTokenUri, String.class);
        JSONObject jsonObjOfAccessToken = new JSONObject(accessTokenRequest);
        String accessToken = jsonObjOfAccessToken.get("access_token").toString();

        //trade your access token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> linkedinDetailRequest = restTemplate.exchange(linedkinDetailUri, HttpMethod.GET, entity, String.class);
        //store json data
        JSONObject jsonObjOfLinkedinDetail = new JSONObject(linkedinDetailRequest.getBody());
        //print json data in console

        ObjectMapper mapper = new ObjectMapper();
        LinkedinData linkedinData = mapper.readValue(jsonObjOfLinkedinDetail.toString(), LinkedinData.class);

        User user = getCurrentUser();

        //store data in the forms
        user.setLastName(linkedinData.getLocalizedLastName());
        user.setFirstName(linkedinData.getLocalizedFirstName());
        userDAO.delete(user);
        userDAO.save(user);
        return "redirect:/admin";
    }


}

