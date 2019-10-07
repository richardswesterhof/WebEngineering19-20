package nl.rug.comgrafic.auth.controller;

import nl.rug.comgrafic.auth.model.Role;
import nl.rug.comgrafic.auth.model.User;
import nl.rug.comgrafic.auth.service.RoleService;
import nl.rug.comgrafic.auth.service.UserService;
import nl.rug.comgrafic.model.job.JobType;
import nl.rug.comgrafic.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private UserService userService;
    private RoleService roleService;
    private JobTypeService jobTypeService;

    @Autowired
    public HomeController (UserService userService,
                           RoleService roleService,
                           JobTypeService jobTypeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.jobTypeService = jobTypeService;
    }

    @GetMapping ("/")
    public String goToLogin () {
        System.out.println ("goToLogin: default setup");
        defaultSetup ();
        return "redirect:/login";
    }

    private void defaultSetup () {
        defaultRoles ();
        defaultUsers ();
        defaultJobTypes ();
    }

    private void defaultRoles () {
        defaultRole ("ADMIN");
        defaultRole ("OPERATOR");
        defaultRole("PUBLISHER");
    }

    private void defaultRole (String name) {
        Role role = roleService.findByName (name);
        if (role != null)
            return;
        role = new Role ();
        role.setRole (name);
        roleService.saveRole (role);
    }

    /**
     * Insert the default users into the database
     */
    private void defaultUsers () {
        defaultUser ("admin", "admin", "ADMIN");
        defaultUser ("printer1", "p1", "OPERATOR");
        defaultUser ("printer2", "p2", "OPERATOR");
        defaultUser ("printer3", "p3", "OPERATOR");
        defaultUser("publisher", "p", "PUBLISHER");
    }

    /**
     * Insert a user into the database
     * @param username The username
     * @param password The password
     * @param roleName The role of the user (ADMIN/OPERATOR)
     */
    private void defaultUser (String username, String password, String roleName) {
        User user = userService.findByUsername (username);
        if (user != null)
            return; // already in the DB
        user = new User ();
        user.setUsername (username);
        user.setPassword (password);
        userService.saveUser (user, roleName);
    }

    /**
     * Insert the default job types (from the Excel table) into the databse
     */
    private void defaultJobTypes(){
        defaultJobType("Arranque");
        defaultJobType("Filos");
        defaultJobType("Cambio caucho");
        defaultJobType("Repetición planchas");
        defaultJobType("Espera cliente");
        defaultJobType("limpieza semanal");
        defaultJobType("SIN TRABAJO");
        defaultJobType("Otros");
    }

    /**
     * Insert a job type into the database
     * @param type the job type
     */
    private void defaultJobType(String type){
        JobType jobType = jobTypeService.findByJobType(type);
        if(jobType != null)
            return; // already in the DB
        jobType = new JobType();
        jobType.setType (type);
        jobTypeService.saveJobType(jobType);
    }
}
