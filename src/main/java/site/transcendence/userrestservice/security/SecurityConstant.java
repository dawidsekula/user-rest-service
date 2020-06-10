package site.transcendence.userrestservice.security;

public class SecurityConstant {

    /*
    Time converting hint sheet

    1 minute    = 60000L
    2 minutes   = 120000L
    5 minutes   = 300000L
    1 hour      = 3600000L
    2 hours     = 7200000L
    12 hours    = 43200000L
    24 hours    = 86400000L
    2 days      = 172800000L
    7 days      = 604800000L
    10 days     = 864000000L
    30 days     = 2592000000L

     */

    public static final Long LOGIN_AUTHENTICATION_EXPIRATION_TIME = 120000L;
    public static final Long PASSWORD_RESET_TOKEN_EXPIRATION_TIME = 300000L;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String TOKEN_SECRET = "token";

    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String LOGIN_URL = "/login";
    public static final String EMAIL_VERIFICATION_URL = "/api/v1/users/email-verification";
    public static final String PASSWORD_RESET_REQUEST_URL = "/api/v1/users/password-reset-request";
    public static final String PASSWORD_RESET_URL = "/api/v1/users/password-reset";


}
