package spring.library.common.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.library.common.service.AbstractBaseService;

@Service
public class PersonServiceImpl extends
    AbstractBaseService<PersonEntity,PersonDTO, PersonRepository> implements PersonService{
  @Autowired
  private PersonRepository repository;

  @Override
  protected PersonRepository getRepository() {
    return repository;
  }

}
