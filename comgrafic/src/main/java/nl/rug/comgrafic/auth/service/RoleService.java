package nl.rug.comgrafic.auth.service;

import nl.rug.comgrafic.auth.model.Role;
import nl.rug.comgrafic.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService (RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName (String name ) {
        return roleRepository.findByRole (name);
    }

    public void saveRole (Role role) {
        System.out.println ("saving role " + role.getRole ());
        roleRepository.save (role);
    }
}
