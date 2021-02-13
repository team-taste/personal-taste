package personaltaste.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import personaltaste.service.security.JwtTokenProvider

/**
 * Spring Security Filter chain 사용 명시
 *
 * @author seungmin
 */
@EnableWebSecurity
class WebSecurityConfig(
        private val jwtTokenProvider: JwtTokenProvider
) : WebSecurityConfigurerAdapter() {

    // todo
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
//    }


    override fun configure(http: HttpSecurity?) {
        http ?: throw Exception()
        http
                .httpBasic().disable()      //  기본설정 해제
                .csrf().disable()           // csrf 토큰 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //  토큰 기반 인증으로, 세션 사용 X
                .and()
                .antMatcher("/api/v1/security/auth")
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)   // jwt 필터 추가
    }

}
