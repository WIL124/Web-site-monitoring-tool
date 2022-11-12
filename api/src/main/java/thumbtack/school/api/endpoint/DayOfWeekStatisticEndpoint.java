package thumbtack.school.api.endpoint;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import thumbtack.school.common.service.DayOfWeekStatisticService;

@RestController
@RequestMapping("/days")
@AllArgsConstructor
public class DayOfWeekStatisticEndpoint {
    private DayOfWeekStatisticService service;

    @GetMapping
    public ModelAndView getAll(Model model) {
        model.addAttribute("stat", service.getAll());
        return new ModelAndView("dayOfWeek");
    }
}
