//package spring.library.common.config.userdetail;
//
//import com.hshop.configuration.PropertiesConfiguration;
//import com.hshop.configuration.dto.RegisterDto;
//import com.hshop.dao.model.UserEntity;
//import com.hshop.dao.repository.UserRepository;
//import com.hshop.dto.ResponseDTO;
//import com.hshop.dto.UserDTO;
//import com.hshop.enums.RoleType;
//import com.hshop.exception.BaseException;
//import io.jsonwebtoken.Jwts;
//import javax.crypto.SecretKey;
//import javax.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Service
//public class UserDetailService implements UserDetailsService {
//  @Autowired
//  private UserRepository userRepository;
//  @Autowired
//  private PasswordEncoder passwordEncoder;
//  @Autowired
//  private PropertiesConfiguration propertiesConfiguration;
//  @Autowired
//  private SecretKey secretKey;
//
//  @Override
//  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//    UserEntity userEntity = userRepository.findByUsername(s);
//
//    if (userEntity == null)
//      throw new UsernameNotFoundException("username not found. check your username !");
//
//    return new UserDetail(userEntity);
//  }
//
////  public ResponseEntity<?> login(UsernameAndPasswordDto loginDTO) throws Exception {
////    if (loginDTO.getUsername() == null) {
////      throw new BaseException(400,"username or email or phone is null.",loginDTO);
////    }
////
////    UserEntity userEntity = userRepository.getUser(loginDTO);
////
////    if (!userEntity.getPassword().equals(loginDTO.getPassword())){
////      throw new BaseException(400,"password is not exactly. check password",null);
////    }
////
////    dto dto = new dto();
////    dto.setId(userEntity.getId());
////    dto.setName(userEntity.getName());
////    dto.setPhone(userEntity.getPhone());
////    dto.setEmail(userEntity.getEmail());
////    dto.setAddress(userEntity.getAddress());
////
////    return new ResponseEntity<>(HttpStatus.OK.value(),"login successful",new ResponseDTO<>(dto));
////  }
//
//  public ResponseDTO<?> register(RegisterDto dto)
//      throws BaseException {
//    UserEntity entity = new UserEntity();
//
//    if (dto.getUsername() == null || dto.getPassword() == null)
//      throw new BaseException(400,"username and password is null",null);
//
//    if(userRepository.existsByUsername(dto.getUsername())){
//      throw new BaseException(400,"username is exist",null);
//    }
//    if (dto.getRole()!= null && dto.getRole().equals(RoleType.ADMIN)){
//      throw new BaseException(400,"role cant admin",null);
//    }
//    else if (dto.getRole() != null)
//      entity.setRole(dto.getRole());
//    else
//      entity.setRole(RoleType.USER);
//
//    entity.setUsername(dto.getUsername());
//    entity.setPassword(passwordEncoder.encode(dto.getPassword()));
//
//    entity.setName(dto.getName());
//    entity.setPhone(dto.getPhone());
//    entity.setAddress(dto.getAddress());
//    entity.setEmail(dto.getEmail());
//
//    userRepository.save(entity);
//
//    return new ResponseDTO<>(HttpStatus.OK.value(),"register successful",entity);
//  }
//
//  public UserDTO detail() throws BaseException {
//    UserDTO dto = new UserDTO();
//    UserEntity entity = getUsernameFromRequest();
//    if (entity == null)
//      throw new BaseException(200,"Unauthorization",null);
//
//    dto.setId(entity.getId());
//    dto.setName(entity.getName());
//    dto.setPhone(entity.getPhone());
//    dto.setEmail(entity.getEmail());
//    dto.setAddress(entity.getAddress());
//    dto.setRole(entity.getRole());
//    dto.setCreatedTime(entity.getCreatedDate());
//    dto.setUpdatedTime(entity.getUpdatedDate());
//
//    return dto;
//  }
//
//  public UserEntity getUsernameFromRequest() {
//    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//    String token = request.getHeader("Authorization").replace(propertiesConfiguration.getTokenPrefix(), "");
//
//    String username = Jwts.parser()
//        .setSigningKey(secretKey)
//        .parseClaimsJws(token).getBody().getSubject();
//
//    return userRepository.findByUsername(username);
//  }
//
//
//}
