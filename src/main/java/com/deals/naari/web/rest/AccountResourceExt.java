package com.deals.naari.web.rest;

import com.deals.naari.domain.Authority;
import com.deals.naari.domain.LoginProfile;
import com.deals.naari.domain.LoginStatus;
import com.deals.naari.domain.User;
import com.deals.naari.repository.UserRepository;
import com.deals.naari.security.AuthoritiesConstants;
import com.deals.naari.security.SecurityUtils;
import com.deals.naari.security.jwt.JWTFilter;
import com.deals.naari.security.jwt.TokenProvider;
import com.deals.naari.service.EmailAlreadyUsedException;
import com.deals.naari.service.LoginProfileService;
import com.deals.naari.service.LoginProfileServiceExt;
import com.deals.naari.service.MailService;
import com.deals.naari.service.MailService;
import com.deals.naari.service.UserService;
import com.deals.naari.service.UsernameAlreadyUsedException;
import com.deals.naari.service.dto.PasswordChangeDTO;
import com.deals.naari.service.dto.UserDTO;
import com.deals.naari.web.rest.UserJWTController.JWTToken;
import com.deals.naari.web.rest.errors.*;
import com.deals.naari.web.rest.errors.BadRequestAlertException;
import com.deals.naari.web.rest.vm.KeyAndPasswordVM;
import com.deals.naari.web.rest.vm.LoginVM;
import com.deals.naari.web.rest.vm.ManagedUserVM;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.security.RandomUtil;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResourceExt {

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResourceExt.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final LoginProfileService loginProfileService;

    private final LoginProfileServiceExt loginProfileServiceExt;

    private final MailService mailService;

    private static final String ENTITY_NAME = "accountResourceExt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AccountResourceExt(
        UserRepository userRepository,
        UserService userService,
        LoginProfileService loginProfileService,
        LoginProfileServiceExt loginProfileServiceExt,
        MailService mailService,
        TokenProvider tokenProvider,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.loginProfileService = loginProfileService;
        this.loginProfileServiceExt = loginProfileServiceExt;
        this.mailService = mailService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/naari-check-user-name")
    @ResponseBody
    public ResponseEntity<String> checkUserAccount(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> user = userRepository.findOneByLogin(userDTO.getLogin());
        if (!user.isPresent()) {
            return new ResponseEntity<>("{\"response\" : \"User Not Found\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"response\" : \"User Found\"}", HttpStatus.OK);
    }

    @PostMapping("/naari-activate")
    @ResponseBody
    public ResponseEntity<String> naariActivateAccount(String username, String activationCode) {
        Optional<User> existingUser = userRepository.findOneByLogin(username);
        if (existingUser.isPresent()) {
            Optional<User> user = userService.activateRegistration(activationCode);
            if (!user.isPresent()) {
                throw new AccountResourceException("No user was found for this activation key");
            } else {
                LoginProfile loginProfile = loginProfileServiceExt.findByUsername(username);
                loginProfile.setActivationCode(activationCode);
                loginProfile.setActivationStatus("activated");
                loginProfileService.save(loginProfile);
                return new ResponseEntity<>("{\"response\" : \"User Profile Activated\"}", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"response\" : \"User Profile Not Activated\"}", HttpStatus.OK);
    }

    //     activate:
    //    Input: activation code, username

    //    UserProfile : activated = set to true;
    //    LoginProfile : activate to true;
    //    return : return success/falilure

    @PostMapping("/naari-register")
    @ResponseBody
    public ResponseEntity<LoginProfile> naariRegisterAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }

        //        User user = loginProfileServiceExt.registerUser(managedUserVM, managedUserVM.getPassword());
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        // {login,memberid- generate,phone-empty, email,password,status,activationCode}
        if (user != null) {
            LoginProfile loginProfile = new LoginProfile();
            loginProfile.setUserName(user.getLogin());
            loginProfile.setMemberId(getAlphaNumericString(8)); //generate
            loginProfile.setEmailId(user.getEmail());
            loginProfile.setUserId(user.getId().toString());
            loginProfile.setPassword(user.getPassword());
            if (user.isActivated()) {
                loginProfile.setActivationStatus("activated");
            } else {
                loginProfile.setActivationStatus("not-activated");
            }

            loginProfile.setActivationCode(user.getActivationKey());
            LoginProfile result = loginProfileService.save(loginProfile);
            mailService.sendActivationEmail(user);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "Registration Success"))
                .body(result);
        } else {
            throw new BadRequestAlertException("Bad Request", ENTITY_NAME, "Registration Failed");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return (
            !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }

    private static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    @PostMapping("/naari-send-activation-code")
    @ResponseBody
    public ResponseEntity<String> naariGetActivationCode(String username, String emailId) {
        Optional<User> user = userRepository.findOneByLogin(username);
        if (user.isPresent()) {
            User u = user.get();
            u.setEmail(emailId);
            userRepository.save(u);
            LoginProfile loginProfile = loginProfileServiceExt.findByUsername(username);
            loginProfile.setEmailId(emailId);
            loginProfileService.save(loginProfile);
            mailService.sendActivationEmail(u);
            return new ResponseEntity<>("{\"response\" : \"Email Update Success\"}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"response\" : \"User not found\"}", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/naari-login")
    @ResponseBody
    public ResponseEntity<LoginStatus> naariLogin(@Valid @RequestBody LoginVM loginVM) {
        Optional<User> user = userRepository.findOneByLogin(loginVM.getUsername());
        LoginStatus status = new LoginStatus();
        if (user.isPresent()) {
            User u = user.get();

            if (u.getLogin().equals(loginVM.getUsername())) {
                LoginProfile lp = loginProfileServiceExt.findByUsername(loginVM.getUsername());
                // status = new LoginStatus();
                status.setEmailId(lp.getEmailId());
                status.setStatus(lp.getActivationStatus());
                status.setUserName(lp.getUserName());
            } else {
                status.setStatus("Invalid User");
            }
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
