package com.example.backend.service;

import com.example.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    // AuthenticationManagerهنا الـ 
    // وظيفته يتأكد أن هذا المستخدم فعلاً عند الايميل والباس الصحيح
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    //هذا الكلاس 
    /*
    وظيفته إنشاء الـ JWT.
    
generateToken(email)
فصلنا هذه الوظيفة في كلاس مستقل 
بدل ما نحط كل شيء داخل AuthService.    
    */

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    /*لماذا نستخدم Constructor؟
هذا هو Constructor Injection.

يعني:

AuthService لا يستطيع العمل بدون
AuthenticationManager و JwtService.

Spring يرى أن AuthService يحتاج:

*/
    /* يعني عند إرسال {
 "email": "doctor@gmail.com",
  "password": "123456"} 
  إلى React 
         يصللان إلى login(email, password)
         ثم إلى 
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )*/

    public String login(String email, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
         /*ما هو UsernamePasswordAuthenticationToken؟
         مجرد object 
         يمثل بيانات الشخص الذي يحاول تسجيل الدخول 
         فالـ username هنا هو email.*/

        return jwtService.generateToken(email);
    }
    //بعد نجاح تسجيل الدخول هنا يتم إنشاء 
    /*JWT = JSON Web Token

وهو token يأخذه 
React
ويستخدمه لاحقًا لإثبات أن المستخدم مسجل الدخول. 
*/
}

/* AuthService نفسه لا يبحث في قاعدة البيانات مباشرة.

هو يقول لـ Spring Security:

تحقق من المستخدم.

Spring Security
 يكون مربوطًا عادةً بـ 
 UserDetailsService
أو آلية Authentication أخرى، وهي التي تعرف كيف تجيب المستخدم من قاعدة البيانات.*/