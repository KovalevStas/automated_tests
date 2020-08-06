package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class StepikTests {

    private final String url = "https://stepik.org/";
    private final String login = "test.user.pikabu@gmail.com";
    private final String password = "pikabu123";

        @BeforeAll
        static void config(){
            Configuration.timeout = 10000;
        }

    @Test
    void unSuccessfulAuthorizationTest() {
        open(url);

        $(".navbar__auth_login").click(); //Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue("123").pressEnter();

        $("ul.sign-form__messages li[role='alert']").shouldBe(visible); // Проверить наличие срообщения об ошибке
    }

    @Test
    void checkProfileTest() {
        open(url);

        $(".navbar__auth_login").click(); //Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();

        $(".navbar__profile-img").shouldBe(enabled).click(); // Проверить наличие аватара пользователя
        $(".drop-down__body a").click(); //Нажатие на пункт "Профиль"
        $(".profile-header-widget__name-wrapper h1").shouldHave(text("Тестовый пользователь")); //Проверка отображения имени пользователя
    }

    @Test
    void searchTest() {
        open(url);

        $(".navbar__auth_login").click(); //Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();

        $(byText("Каталог")).click();
        $(by("placeholder","Поиск по каталогу")).setValue("Уравнения").pressEnter(); //Вводим запрос в поле поиска
        $(".course-pack").shouldHave(text("Уравнения")); //Проверяем результат поиска
    }

    @Test
    void joinAndExitCourseTest() {
        open(url);

        $(".navbar__auth_login").click(); //Нажатие кнопки "Войти"
        $(byName("login")).setValue(login);
        $(byName("password")).setValue(password).pressEnter();


        $(byText("Машинное обучение и управление проектами в IT для преподавателей")).click(); //Найти курс и перейти на его страницу
        $(byText("Поступить на курс")).click();  //Записаться на курс
        $(".lesson-sidebar__course-title").shouldBe(visible); //Дождаться загрузки страницы первого урока
        //Открыть список меню пользователя и перейти в пункт "Мои курсы"
        $(".navbar__profile-img").click();
        $(".drop-down__body a", 1).click();

        $(".course-widget__main-info a")
                .shouldHave(text("Машинное обучение и управление проектами в IT для преподавателей")); //Проверить наличие курса в списке курсов пользователя

        //Покинуть курс
        $(".course-widget__menu").click();
        $(byText("Покинуть")).click();
        switchTo().alert().accept(); //Подтвердить действие

        $$(".course-widget__menu-toggler").shouldHave(size(0)); //Проверить, что у объекта курса пропали контролы
        refresh(); //Обновить страницу
        $$(".course-widget__main-info").shouldHave(size(0)); //Проверить,что список курсов пустой
    }

    @AfterEach
    void exit() {
        closeWebDriver();
    }
}
