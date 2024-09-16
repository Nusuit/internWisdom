package wisdom.intern.task2.security.jwt;

import
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    private String jwtSecret = "secret";
}
