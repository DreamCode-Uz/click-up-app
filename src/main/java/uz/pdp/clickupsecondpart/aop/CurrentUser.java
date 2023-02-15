package uz.pdp.clickupsecondpart.aop;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Documented
@Target(value = {ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {
}
