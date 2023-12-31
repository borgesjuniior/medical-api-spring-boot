package med.vol.api.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import med.vol.api.entities.User;

@Service
public class TokenService {

  @Value("${security.token.secret}")
  private String secret;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("API")
          .withSubject(user.getLogin()) // passes the user the token
          .withExpiresAt(expireAt()) // token expiration time
          .sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error generating token", exception);
    }
  }

  public String getSubject(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);

      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("API")
          .build();

      return verifier.verify(token).getSubject();
    } catch (JWTVerificationException exception) {
      throw new RuntimeException("Invalid token");
    }
  }

  private Instant expireAt() {
    return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
  }
}
