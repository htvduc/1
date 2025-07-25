package com.example.source1.controller;

import com.example.source1.entity.Computer;
import com.example.source1.entity.User;
import com.example.source1.repository.ComputerRepository;
import com.example.source1.entity.Manufacturer;
import com.example.source1.repository.ManufacturerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.math.BigDecimal;
import java.time.Year;

import java.util.List;

@Controller
public class ComputerController {
    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @GetMapping("/home")
    public String listComputers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || (user.getRole() != User.Role.ADMIN && user.getRole() != User.Role.STAFF)) {
            model.addAttribute("error", "You don't have permission to access this function");
            return "error";
        }
        List<Computer> computers = computerRepository.findAll();
        model.addAttribute("computers", computers);
        return "computer_list";
    }

    @GetMapping("/computers/add")
    public String showAddForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            model.addAttribute("error", "You don't have permission to access this function");
            return "error";
        }
        model.addAttribute("computer", new Computer());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        return "computer_add";
    }

    @PostMapping("/computers/add")
    public String addComputer(@Valid @ModelAttribute("computer") Computer computer,
                             @RequestParam(value = "manufacturer", required = false) Integer manufacturerId,
                             BindingResult result,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            model.addAttribute("error", "You don't have permission to access this function");
            return "error";
        }
        
        // Set manufacturer from ID
        if (manufacturerId != null) {
            Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElse(null);
            if (manufacturer == null) {
                result.rejectValue("manufacturer", null, "Invalid manufacturer selected");
            } else {
                computer.setManufacturer(manufacturer);
            }
        }
        
        // Validate các trường theo yêu cầu đề bài
        if (computer.getComputer_model() == null || computer.getComputer_model().isBlank()
                || computer.getComputer_model().length() < 5 || computer.getComputer_model().length() > 50) {
            result.rejectValue("computer_model", null, "Model length must be between 5 and 50 characters");
        }
        if (computer.getType() == null || computer.getType().isBlank()
                || computer.getType().length() < 2 || computer.getType().length() > 50) {
            result.rejectValue("type", null, "Type length must be between 2 and 50 characters");
        }
        if (computer.getPrice() == null || computer.getPrice().compareTo(BigDecimal.valueOf(100)) < 0 
                || computer.getPrice().compareTo(BigDecimal.valueOf(100000)) > 0) {
            result.rejectValue("price", null, "Price must be between 100 and 100,000");
        }
        int year = computer.getProduction_year() == null ? 0 : computer.getProduction_year();
        int currentYear = Year.now().getValue();
        if (year < 1990 || year > currentYear) {
            result.rejectValue("production_year", null, "Year must be between 1990 and " + currentYear);
        }
        if (computer.getManufacturer() == null) {
            result.rejectValue("manufacturer", null, "Manufacturer is required");
        }
        if (result.hasErrors()) {
            model.addAttribute("manufacturers", manufacturerRepository.findAll());
            return "computer_add";
        }
        computerRepository.save(computer);
        return "redirect:/home";
    }
} 