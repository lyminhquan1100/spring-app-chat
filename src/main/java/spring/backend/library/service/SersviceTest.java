//package spring.library.common.service;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import spring.library.common.dao.model.DsThietBiEntity;
//import spring.library.common.dao.repository.DsThietBiRepository;
//
//@Service
//@EnableScheduling
//public class SersviceTest {
//  @Autowired
//  private DsThietBiRepository dsThietBiRepository;
//
//  @Scheduled(fixedDelay = 10000)
//  public void test(){
//    Sort s = Sort.by(Direction.DESC,"id");
//    Pageable page = PageRequest.of(0,20,s);
//    List<DsThietBiEntity> l = dsThietBiRepository.searchAll("",page);
//  }
//}
