package spring.library.common.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.library.common.controller.BaseController;

@RestController
@RequestMapping("/person")
public class PersonController extends BaseController<PersonDTO,PersonService> {
  @Autowired
  private PersonService service;

  @Override
  public PersonService getService() {
    return service;
  }
}
