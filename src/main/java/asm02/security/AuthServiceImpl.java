package asm02.security;

import asm02.dao.CompanyDao;
import asm02.dao.UserDao;
import asm02.entity.eUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
  @Autowired
  private UserDao userDao;
  @Autowired
  private CompanyDao companyDao;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AuthUser authUser = userDao.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email));

    if (authUser.getRole() == eUserRole.RECRUITER) {
      authUser.setCompanyId(
          companyDao
              .findByRecruiter(authUser.getId())
              .orElseThrow(() -> new RuntimeException("Recruiter [" + authUser.getId() + "] with no company!"))
              .getId());
    }
    return authUser;
  }

  @Override
  public void refreshAuthentication(String email) {
    AuthUser updatedAuthUser = (AuthUser) loadUserByUsername(email);
    UsernamePasswordAuthenticationToken updatedAuth = new UsernamePasswordAuthenticationToken(
        updatedAuthUser,
        updatedAuthUser.getPassword(),
        updatedAuthUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(updatedAuth);
  }
}
