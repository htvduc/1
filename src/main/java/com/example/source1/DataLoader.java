package com.example.source1;

import com.example.source1.entity.User;
import com.example.source1.entity.Manufacturer;
import com.example.source1.entity.Computer;
import com.example.source1.repository.UserRepository;
import com.example.source1.repository.ManufacturerRepository;
import com.example.source1.repository.ComputerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ComputerRepository computerRepository;
//    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, ManufacturerRepository manufacturerRepository, ComputerRepository computerRepository) {
        this.userRepository = userRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.computerRepository = computerRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(new User(null, "admin@laptopshop.com", "@1", User.Role.ADMIN));
            userRepository.save(new User(null, "staff@laptopshop.com", "@2", User.Role.STAFF));
            userRepository.save(new User(null, "member@laptopshop.com", "@3", User.Role.CUSTOMER));
        }
        if (manufacturerRepository.count() == 0) {
            Manufacturer dell = manufacturerRepository.save(new Manufacturer(null, "Dell", "USA", null));
            Manufacturer lenovo = manufacturerRepository.save(new Manufacturer(null, "Lenovo", "China", null));
            Manufacturer hp = manufacturerRepository.save(new Manufacturer(null, "HP", "USA", null));

            Computer c1 = new Computer(null, "XPS 13", "Ultrabook", 2023, BigDecimal.valueOf(1299.99), dell);
            Computer c2 = new Computer(null, "ThinkPad X1 Carbon", "Business Laptop", 2023, BigDecimal.valueOf(1499.99), lenovo);
            Computer c3 = new Computer(null, "Pavilion 15", "Consumer Laptop", 2022, BigDecimal.valueOf(699.99), hp);
            Computer c4 = new Computer(null, "Inspiron 14", "Budget Laptop", 2023, BigDecimal.valueOf(549.99), dell);

            computerRepository.save(c1);
            computerRepository.save(c2);
            computerRepository.save(c3);
            computerRepository.save(c4);
        }
    }
} 