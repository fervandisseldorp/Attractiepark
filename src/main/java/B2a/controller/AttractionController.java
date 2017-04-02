package B2a.controller;

import B2a.domain.Attraction.Attraction;
import B2a.domain.Attraction.Rollercoaster;
import B2a.service.abstractService.AttractionManagerIF;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AttractionController {
    AttractionManagerIF attractionManagerIF;

    public AttractionController(AttractionManagerIF attractionManagerIF){
        this.attractionManagerIF = attractionManagerIF;
    }

    @RequestMapping(value = "attraction/attractionsList", method = RequestMethod.GET)
    public ModelAndView attractionsList(Model model) {
        Iterable<Attraction> attractions = attractionManagerIF.findAllAttractions();
        return new ModelAndView("/attraction/attractionsList", "attractions", attractions);
    }


    @RequestMapping(value = "attraction/info/{id}", method = RequestMethod.GET)
    public ModelAndView info(@PathVariable("id") Attraction attraction){
        return new ModelAndView("/attraction/info", "attraction", attraction);
    }

    @RequestMapping(value = "attraction/adminAttractionsList", method = RequestMethod.GET)
    public ModelAndView adminAttractionList(){
        Iterable<Attraction> attractions = attractionManagerIF.findAllAttractions();
        return new ModelAndView("/attraction/adminAttractionsList", "attractions", attractions);
    }


//    @RequestMapping(value = "attraction/adminAttractionsList", method = RequestMethod.POST)
//    public ModelAndView adminAttractionList(Model model, @RequestParam(value="action", required = true) String action){
//        Iterable<Attraction> attractions = attractionManagerIF.findAllAttractions();
//        System.out.println("test");
//
//        return new ModelAndView("/attraction/adminAttractionsList", "attractions", attractions);
//    }

    @RequestMapping(value = "attraction/adminAttractionsList", method = RequestMethod.POST)
    public String adminAttractionsList(Rollercoaster attraction, @RequestParam(value="action", required = true) String action){
        //Parameter should be Attraction, but can't instantiate abstract class..
        System.out.println("actie = " + action);
        attractionManagerIF.changeState(attraction, action);
        return "redirect:/attraction/adminAttractionsList";
    }

//
//    @RequestMapping(value = "/attraction/attractionAdmin", method = RequestMethod.GET)
//    public ModelAndView attractionAdmin(Attraction attraction) {
//
//        return new ModelAndView("attractionAdmin", "Attraction", null);
//    }


//    @RequestMapping(value = "attraction/attractionForm", method = RequestMethod.GET)
//    public String attractionForm(Model model) {
//        //model.addAttribute("rollercoasterForm", new Rollercoaster());
//
//        return "attraction/attractionForm";
//    }
//
//    @RequestMapping(value = "/attraction/rollercoasterForm", method = RequestMethod.GET)
//    public ModelAndView rollercoasterForm(Attraction attraction) {
//        return new ModelAndView("attraction/rollercoasterForm", "attraction", attraction);
//    }


    @RequestMapping(value = "/attraction/rollercoasterForm", method = RequestMethod.GET)
    public ModelAndView rollercoasterForm(Rollercoaster rollercoaster) {
        return new ModelAndView("attraction/rollercoasterForm", "rollercoaster", null);
    }

    @RequestMapping(value = "/attraction/rollercoasterForm", method = RequestMethod.POST)
    public String rollercoasterForm(Rollercoaster rollercoaster, BindingResult result) {
        System.out.println("testing rollercoaster Post");
        if (result.hasErrors()) {
            return "attraction/attractionsList";
        }
        attractionManagerIF.saveAttraction(rollercoaster);

        return "attraction/attractionsList";
    }



    @RequestMapping(value = "/attraction/attractionChooser", method = RequestMethod.GET)
    public String attractionChooser(){
        return "attraction/attractionChooser";
    }

    @RequestMapping(value = "/attraction/attractionChooser", method = RequestMethod.POST)
    public String attractionChooser(Model model, @RequestParam(value="action", required = true) String action) {
        attractionManagerIF.createNewAttraction(action);

        return "redirect:/attraction/attractionsList";
    }

//
//    public void testStartState(){
//
//        Attraction b = new LogFlume();
//        b.setState( new RunningState(b));
//
//    }
}
