package eti.lucasgomes.mercadolivro.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Claim
import eti.lucasgomes.mercadolivro.exception.AuthenticationException
import eti.lucasgomes.mercadolivro.exception.Error
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generateToken(id: Int): String {
        return JWT.create()
            .withSubject(id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + expiration!!))
            .sign(Algorithm.HMAC512(secret!!.toByteArray()))
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        return !(claims["sub"]?.isNull == true || claims["exp"]?.isNull == true || Date().after(claims["exp"]?.asDate()))
    }

    private fun getClaims(token: String): MutableMap<String, Claim> {
        try {
            return JWT.require(Algorithm.HMAC512(secret!!.toByteArray())).build().verify(token).claims
        } catch (e: Exception) {
            throw AuthenticationException(Error.Auth.Unauthorized)
        }
    }

    fun getSubject(token: String): String {
        return try {
            getClaims(token)["sub"]!!.asString()
        } catch (e: Exception) {
            throw AuthenticationException(Error.Auth.Unauthorized)
        }
    }
}