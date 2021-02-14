[![Build Status](https://travis-ci.com/andreyDelay/crud-example.svg?branch=master)](https://travis-ci.com/andreyDelay/crud-example)

Необходимо реализовать консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:
    
      Customer
      Specialty
      Account
      AccountStatus (enum ACTIVE, BANNED, DELETED)

      Customer-> Set<Specialty> specialties+ Account account
      Account -> AccountStatus
      
Требования:

* Придерживаться шаблона MVC (пакеты model, repository, service, controller, view);
* Для сборки проекта использовать Maven;
* Для взаимодействия с БД - Hibernate;
* Инициализация БД должна быть реализована с помощью flyway;
* Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito);
* Результатом выполнения задания должен быть репозиторий на github, с использованием Travis (https://travis-ci.org/)
  и отображением статуса сборки проекта; 
* Необходимо предоставить возможность переключения между имплементациями jdbc и hibernate;
* Для импорта библиотек использовать Maven.


Технологии: 

        Java, 
        PostgeSQL, 
        Hibernate, 
        Maven, 
        Flyway, 
        JUnit, 
        Mockito

