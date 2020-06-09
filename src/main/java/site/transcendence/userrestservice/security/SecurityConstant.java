package site.transcendence.userrestservice.security;

public class SecurityConstant {

    public static final Long LOGIN_AUTHENTICATION_EXPIRATION_TIME = 864000000L; //10 days
    public static final Long PASSWORD_RESET_TOKEN_EXPIRATION_TIME = 3600000L; //1 hour

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String TOKEN_SECRET = "tokenSecret";

    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String LOGIN_URL = "/login";
    public static final String EMAIL_VERIFICATION_URL = "/api/v1/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/api/v1/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/api/v1/users/password-reset";


}
