package com.example.demo.security



import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class JwtUtil {

  private val secretKey = "mi_clave_secreta_muy_segura_que_debes_cambiar"

  fun generateToken(username: String): String {
    println("generateToken")
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(Date())
      .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
      .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray(StandardCharsets.UTF_8))
      .compact()
  }

  // Puedes agregar método para validar y extraer información del token si necesitas
}