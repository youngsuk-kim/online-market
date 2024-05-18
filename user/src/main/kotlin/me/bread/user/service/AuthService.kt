package me.bread.user.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object AuthService {

    fun authenticate(userResult: UserResult) = run {
        generateToken(userResult.email, userResult.password)
    }

    fun validate(email: String) {
    }

    private fun generateToken(userId: String, secret: String): String {
        val expirationTime = 1000 * 60 * 60 * 24
        val issuedAt = Date()
        val expiration = Date(issuedAt.time + expirationTime)

        return JWT.create()
            .withSubject(userId)
            .withIssuedAt(issuedAt)
            .withExpiresAt(expiration)
            .withIssuer("your-issuer")
            .sign(Algorithm.HMAC256(secret))
    }
}
