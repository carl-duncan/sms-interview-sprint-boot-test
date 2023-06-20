package com.cdapps.sprintbootassessment.security;

import com.cdapps.sprintbootassessment.enums.Role;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Value("${jwt.public.key}")
	RSAPublicKey publicKey;

	@Value("${jwt.private.key}")
	RSAPrivateKey privateKey;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/signIn").permitAll()
						.requestMatchers("/signUp").permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
						.anyRequest().authenticated()
				)
				.csrf((csrf) -> csrf.ignoringRequestMatchers("/signIn")
						.ignoringRequestMatchers("/signUp")
						.ignoringRequestMatchers("/v3/api-docs")
						.ignoringRequestMatchers("/swagger-ui/index.html"))
				.httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
						.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
				);
		return http.build();
	}

	public OAuth2TokenValidator<Jwt> adminValidator() {
		final Role[] roles = Role.values();
		return new JwtClaimValidator<ArrayList<String>>(
				OAuth2TokenIntrospectionClaimNames.SCOPE,
				scope -> {
					for (Role role : roles) {
						if (scope.contains(role.toString())) {
							return true;
						}
					}
					return false;
				}
		);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		final NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(this.publicKey).build();
		decoder.setJwtValidator(adminValidator());

		return decoder;
	}

	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
		JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwkSource);
	}
}