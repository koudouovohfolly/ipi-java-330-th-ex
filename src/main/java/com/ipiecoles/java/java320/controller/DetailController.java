package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Commercial;
import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.service.EmployeService;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/employes")
public class DetailController {
    @Autowired
    private EmployeService employeService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    public String detail(@PathVariable(value = "id") Long id, final ModelMap model) {
        Employe employe = employeService.findById(id);
        model.put("employe", employe);
        employe.getClassName();
        return ("detail");
    }

    @RequestMapping(method = RequestMethod.GET, value = "",params = "matricule")
    public String searchByMatricule(@RequestParam String matricule, final ModelMap model) {
        model.put("employe", employeService.findMyMatricule(matricule));
        return "detail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new/{typeEmploye}")
    public String newEmploye(@PathVariable String typeEmploye, final ModelMap model) {
        switch (typeEmploye.toLowerCase()) {
            case "commercial":
                model.put("employe", new Commercial());
                break;
            case "technicien":
                model.put("employe", new Technicien());
                break;
            case "manager":
                model.put("employe", new Manager());
                break;
        }
        return "detail";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/technicien", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createTechnicien(Technicien employe){
        if(employe.getId() != null){
            //Modification
            employe = employeService.updateEmploye(employe.getId(), employe);
        } else {
            //Création
            employe = employeService.creerEmploye(employe);
        }
        return new RedirectView("/employes/" + employe.getId());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/commercial", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createCommercial(Commercial employe){
        if(employe.getId() != null){
            //Modification
            employe = employeService.updateEmploye(employe.getId(), employe);
        } else {
            //Création
            employe = employeService.creerEmploye(employe);
        }
        return new RedirectView("/employes/" + employe.getId());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/manager", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createManager(Manager employe){
        if(employe.getId() != null){
            //Modification
            employe = employeService.updateEmploye(employe.getId(), employe);
        } else {
            //Création
            employe = employeService.creerEmploye(employe);
        }
        return new RedirectView("/employes/" + employe.getId());
    }
    @RequestMapping(method = RequestMethod.GET, value = "")
    public String listEmployes(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortProperty,
            @RequestParam String sortDirection,
            final ModelMap modelMap){
        Page<Employe> employes = employeService.findAllEmployes(page, size, sortProperty, sortDirection);
        modelMap.put("employes", employes);
        modelMap.put("start", page * size + 1);
        modelMap.put("end", page * size + employes.getNumberOfElements());
        return "list";
    }
}
