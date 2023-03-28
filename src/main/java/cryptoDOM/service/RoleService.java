package cryptoDOM.service;

import cryptoDOM.entity.Role;
import cryptoDOM.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getByRole(String role) {
        return roleRepository.getByRole(role);
    }
}
