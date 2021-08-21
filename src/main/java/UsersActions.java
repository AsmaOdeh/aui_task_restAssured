import io.restassured.response.Response;


public class UsersActions {

    public static int statusCode (Response testResponse){
        int statusCode = testResponse.getStatusCode();
        return statusCode;
    }


}
