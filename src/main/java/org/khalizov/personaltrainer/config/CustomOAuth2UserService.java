package org.khalizov.personaltrainer.config;

import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.UserType;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = extractEmail(registrationId, attributes);
        String firstName = extractFirstName(registrationId, attributes);
        String lastName = extractLastName(registrationId, attributes);

        User user = userRepository.findByEmail(email)
            .orElseGet(() -> createNewUser(email, firstName, lastName, registrationId));

        return new CustomOAuth2User(oauth2User, user);
    }

    private String extractEmail(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "google", "microsoft" -> (String) attributes.get("email");
            case "facebook" -> (String) attributes.get("email");
            default -> null;
        };
    }

    private String extractFirstName(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> (String) attributes.get("given_name");
            case "facebook" -> {
                String name = (String) attributes.get("name");
                yield name != null ? name.split(" ")[0] : "User";
            }
            case "microsoft" -> (String) attributes.get("givenName");
            default -> "User";
        };
    }

    private String extractLastName(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> (String) attributes.get("family_name");
            case "facebook" -> {
                String name = (String) attributes.get("name");
                String[] parts = name != null ? name.split(" ") : new String[]{};
                yield parts.length > 1 ? parts[parts.length - 1] : "";
            }
            case "microsoft" -> (String) attributes.get("surname");
            default -> "";
        };
    }

    private User createNewUser(String email, String firstName, String lastName, String provider) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickname(email.split("@")[0] + "_" + provider);
        user.setStatus(Status.ACTIVE);
        user.setUserType(UserType.CLIENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(UUID.randomUUID().toString()));

        return userRepository.save(user);
    }
}