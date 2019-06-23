# dom
Проект собирается через Maven. Расположение jar ./targer/test-1.0-SNAPSHOT.jar

## Модель
Сущность состоит лишь из 2-х полей id(UUID) и amount(баланс счета)

## API
### Основное
* Положить деньги на счет
POST /account/{id}/increase?step=<POSITIVE_LONG>
* Снять деньги со счета
POST /account/{id}/decrease?step=<POSITIVE_LONG>
* Перечислить с одного счета на другой
POST /account/exchange?from=<UUID>&to<UUID>&step=<POSITIVE_LONG>

На все запросы в случае успеха возвращается пустое тело со статусом 200.

### Дополнительное
* Получение всех через паджинацию
GET /account?page=<NUMBER>&limit<NUMBER>
* Получение одного
GET /account/{id}
* Создать аккаунт
POST /account в теле метода json
* Обновить аккаунт
PUT /account/{id} в теле метода json 

## Дополнения
* В бд есть два заранее созданных значения
* В корне директории лежит коллекция для [Postman](https://www.getpostman-beta.com/)
