package personaltaste.service.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * jwt token 생성, parse 등 로직
 *
 * @author seungmin
 */
@Component
class JwtTokenProvider(
        private val userDetailService: UserDetailsService
) {
    private val logger = KotlinLogging.logger {}

    // todo use properties, base 64
    private val key: String = "personalTaste"

    /**
     * 30 분
     */
    private val tokenValidTime: Long = 30 * 60 * 1000L

    fun createToken(userEmail: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(userEmail)
        claims["roles"] = roles
        val now = Date()
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailService.loadUserByUsername(this.extractUserEmail(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun resolveToken(request: HttpServletRequest): String {
        return request.getHeader("x-auth-token")
    }

    fun validateToken(token: String): Boolean {
        return try {
            this.parseJwtBody(token)
                    .expiration
                    .after(Date())
        } catch (e: Exception) {
            logger.error("{}", e)
            false
        }
    }

    private fun extractUserEmail(token: String): String {
        return this.parseJwtBody(token).subject
    }

    private fun parseJwtBody(token: String): Claims {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .body
    }

}
