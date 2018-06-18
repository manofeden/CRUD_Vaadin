# crudpostgres
используемая платформа: Spring (Spring Boot 2.0)
интерфейс: Vaadin 8.4
сервер: Apache Tomcat 8.5
База Данных: PostgreSql 9.6
IDE: Eclipse 4.7
Сборка: Maven 3.5

Сервис по учету обслуживаемых физических лиц (ФЛ).
Сервис предоставляет 3 метода: создание, поиск и редактирование (замена) данных ФЛ.
Набор данных ФЛ:
- СНИЛС
- ФИО (одной строкой)
- дата рождения

Cобранный проект упакован в файл CrudVaadin-1.war 

Приложение будет пытаться подключиться к БД PostgreSql с настройками:
url=jdbc:postgresql://localhost:5432/polyclinic
username=admin
password=admin