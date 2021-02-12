package personaltaste.service.model.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import personaltaste.entity.User
import java.util.stream.Collectors

/**
 *
 *
 * @author seungmin
 */
class CustomUserDetails (
        private val email: String,
        private val password: String,
        private val roles: List<String>
): UserDetails {

    companion object {
        fun of(user: User) : CustomUserDetails {
            return CustomUserDetails(
                    email = user.email,
                    password = user.password,
                    roles = mutableListOf("USER")
            )
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.stream()
                .map(::SimpleGrantedAuthority)
                .collect(Collectors.toSet())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
