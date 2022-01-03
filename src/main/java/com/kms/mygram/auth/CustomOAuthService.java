package com.kms.mygram.auth;

import com.kms.mygram.user.entity.User;
import com.kms.mygram.common.exception.OAuthException;
import com.kms.mygram.user.repository.UserRepository;
import com.kms.mygram.user.service.AuthService;
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
public class CustomOAuthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        User user = userRepository.findByEmail(email).orElse(null);
        if ("facebook".equals(registrationId))
            return faceBookUser(user, attributes);
        return googleUser(user, attributes);
    }

    OAuth2User faceBookUser(User user, Map<String, Object> attributes) {
        if (user == null) {
            user = User.builder()
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .username("facebook_"  + attributes.get("id"))
                    .password(UUID.randomUUID().toString())
                    .oauth("facebook")
                    .build();
            return new Principal(authService.signup(user), attributes);
        }
        if ("facebook".equals(user.getOauth())) {
            user.getAuthorities();
            return new Principal(user, attributes);
        }
        throw new OAuthException("Email duplicate");
    }

    OAuth2User googleUser(User user, Map<String, Object> attributes) {
        if (user == null) {
            user = User.builder()
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .username("google_" + attributes.get("sub"))
                    .password(UUID.randomUUID().toString())
                    .oauth("google")
                    .build();
            return new Principal(authService.signup(user), attributes);
        }
        if ("google".equals(user.getOauth())) {
            user.getAuthorities();
            return new Principal(user, attributes);
        }
        throw new OAuthException("Email duplicate");
    }
}
