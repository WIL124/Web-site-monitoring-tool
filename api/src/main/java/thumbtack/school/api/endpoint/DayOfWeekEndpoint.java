package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import thumbtack.school.api.service.DayOfWeekService;
import thumbtack.school.reporter.model.DayOfWeekStatistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class DayOfWeekEndpoint {
    DayOfWeekService service;
    @GetMapping("/all")
    public ModelAndView getAll(Model model) {
        model.addAttribute("stat", service.getAll());
        return new ModelAndView("dayOfWeek");
    }
}
