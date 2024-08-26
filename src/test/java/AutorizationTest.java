import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import сonstans.AutorizationUser;
import сonstans.UserData;
import сonstans.UserDelete;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class AutorizationTest extends HttpPage {
    private String email;
    private String name;
    private String password;
    private String accessToken;
    private UserData user;

    @Before

    public void setUp() {
        baseURL();
        user = UserData.makeRandomUser();
        BasicUser.buildUser(user);
        email = user.getEmail();
        name = user.getName();
        password = user.getPassword();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void testAutorizationUser() {
        AutorizationUser autorizationUser = new AutorizationUser(email, password);
        Response response = BasicUser.autorizationUser(autorizationUser);
        response
                .then().assertThat().statusCode(SC_OK).body("success", equalTo(true));
        accessToken = response.jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Авторизация  с неверным логином и паролем")
    public void testLoginUserWrongData() {
        AutorizationUser autorizationUser = new AutorizationUser(RandomStringUtils.randomAlphabetic(8) + "@yandex.ru", RandomStringUtils.randomAlphabetic(5));
        Response response = BasicUser.autorizationUser(autorizationUser);
        response.then().assertThat().statusCode(SC_UNAUTHORIZED).body("success", equalTo(false));
        accessToken = response.jsonPath().getString("accessToken");
    }

    }
