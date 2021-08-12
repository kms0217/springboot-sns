package com.kms.mygram.auth;

import com.kms.mygram.domain.User;
import com.kms.mygram.exception.OAuthException;
import com.kms.mygram.repository.UserRepository;
import com.kms.mygram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaceBookOAuthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User facebookUser = super.loadUser(userRequest);
        Map<String, Object> attributes = facebookUser.getAttributes();
        String email = (String) attributes.get("email");
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = User.builder()
                    .name((String) attributes.get("name"))
                    .email(email)
                    .username("facebook_" + attributes.get("id"))
                    .password(UUID.randomUUID().toString())
                    .oauth("facebook")
                    .build();
            return new Principal(authService.signup(user), attributes);
        }
        if ("facebook".equals(user.getOauth())){
            user.getAuthorities();
            return new Principal(user, facebookUser.getAttributes());
        }
        throw new OAuthException("Email duplicate");
    }
}
