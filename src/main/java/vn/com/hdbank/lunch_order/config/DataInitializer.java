package vn.com.hdbank.lunch_order.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.com.hdbank.lunch_order.entity.Role;
import vn.com.hdbank.lunch_order.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Tạo role USER nếu chưa tồn tại
        if (!roleRepository.existsByName("ROLE_USER")) {
            Role userRole = new Role("ROLE_USER", "Default user role");
            roleRepository.save(userRole);
        }

        // Tạo role ADMIN nếu chưa tồn tại
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            Role adminRole = new Role("ROLE_ADMIN", "Administrator role");
            roleRepository.save(adminRole);
        }
    }
}