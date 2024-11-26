package coffee_manager.config;

public class Constants {
    private Constants() {
    }

    public static final  boolean IS_CROSS_ALLOW = true;

    public static final long JWT_EXPIRATION = 168*60*60;
    public static final String[] ATTRIBUTES_TO_TOKEN = new String[] {
            "userId",
            "userFullName",
            "email",
            "userRole"
    };
    public static final String JWT_SECRET = "coffee";
    //config endpoints public
    public static final String[] ENDPOINTS_PUBLIC = new String[] {
            "/login/**",
            "/register/**",
            "/error/**",
            "/home/**",
            "/reset_password/**"
    };
    //config endpoints for user role
    public static final String[] ENDPOINTS_WITH_ROLE = new String[] {
            "/user/**",
            "/game/**",
            "/category/**",
            "/userGame/**",
            "/gamingHistory"
    };
    public static final String[] ENDPOINTS_WITH_ROLE_ADMIN = new String[] {
            "/admin/**",
    };

    // gửi mail
    public static final String SUBJECT_MAIL = "Xác nhận thông tin khách hàng!";
    public static final String REGISTER_FALSE = "Register thất bại!";


}
