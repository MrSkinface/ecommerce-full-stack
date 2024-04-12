package ua.mike.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.mike.ecommerce.persistence.entity.State;
import ua.mike.ecommerce.persistence.repository.StateRepository;

import java.util.List;

/**
 * Created by mike on 12.04.2024 13:14
 */
@RestController
@RequestMapping("api/state")
@RequiredArgsConstructor
public class StateController {

    private final StateRepository stateRepository;

    @GetMapping
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }
}
