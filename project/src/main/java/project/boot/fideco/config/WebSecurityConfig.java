package project.boot.fideco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import project.boot.fideco.filter.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(CsrfConfigurer::disable)
//                .httpBasic(HttpBasicConfigurer::disable)

				.csrf(csrf -> csrf.disable())
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						request -> request
						// 공지사항
						.requestMatchers("/NoticeSelect", "/NoticeSelectDetail", "/NoticeSearch").permitAll()
						.requestMatchers("/NoticeInsert", "/NoticeUpdate", "/NoticeDelete").hasRole("ADMIN")
						// 상품
						.requestMatchers("/products/productView").permitAll() // 모든 사용자에게 허용
						.requestMatchers("/products/productOrder/**").permitAll() // 모든 사용자에게 허용
						.requestMatchers("/products/**").hasRole("ADMIN") // 관리자만 접근 가능
						//회원목록
						.requestMatchers("/member/memberList").hasRole("ADMIN") // 관리자만 접근 가능
						//장바구니목록
						.requestMatchers("/cart/list").hasRole("ADMIN") // 관리자만 접근 가능
						//주문목록
						.requestMatchers("/orders/list").hasRole("ADMIN") // 관리자만 접근 가능
						.requestMatchers("/orders/update").hasRole("ADMIN") // 관리자만 접근 가능
						.anyRequest().permitAll())
						//403 접근제한
						.exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/access-denied")) // Custom Access Denied Page 설정
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
	
}
