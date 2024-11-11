package asm02.security;

import asm02.dao.CompanyDao;
import asm02.dao.UserDao;
import asm02.entity.eUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService   implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser authUser = userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if (authUser.getRole() == eUserRole.RECRUITER) {
            authUser.setCompanyId(
                    companyDao
                            .findByRecruiter(authUser.getId())
                            .orElseThrow(() -> new RuntimeException("Recruiter ["+authUser.getId()+"] with no company!"))
                            .getId()
            );
        }
        return authUser;
    }
}
