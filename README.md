Сборка с помощью Maven: 
mvn package
Запуск (из директории проекта):
`java -jar target/spa-1.0-SNAPSHOT-all.jar`

По умолчанию веб-приложение стартует на `localhost:8080`.

Краткое описание ресурсов:

Пользователи
* GET /api/users - список пользователей
* GET /api/users/<id:uuid> - пользоваль
* POST /api/users - создание пользователя
формат: 
```
{ “name” : “имя” }
```
* PUT /api/users/<id:uuid> - обновление пользователя
* DELETE /api/users/<id:uuid> - не реализовано
* GET /api/users/<id:uuid>/accounts - список счетов пользователя
* GET /api/users<id:uuid>/transactions - список транзакций пользователя

Счета
* GET /api/accounts - список cчетов
* GET /api/accounts/<id:uuid> - счет
* POST /api/accounts - создание счета
формат: 
```
{
    "number" : "Основной",
    "initialBalance" : 1000,
    "user": {
        "type" : "user",
        "id" :"466295f8-2b2c-11e6-a72b-a99550b1b1a0"
}
```
* PUT /api/accounts/<id:uuid> - не реализовано
* DELETE /api/accounts/<id:uuid> - не реализовано

Транзакции
* GET /api/transactions - список транзакций
* GET /api/transactions/<id:uuid> - транзакция
* POST /api/transactions - создание транзакции
формат: 
```
{
  "senderAccount": {
    "type": "account",
    "id": "026e55e8-2b42-11e6-b44f-313df512c604"
  },
  "receiverAccount": {
    "type": "account",
    "id": "0a43a549-2b42-11e6-b44f-837c59903f9c"
  },
  "amount": 100
}
```
* PUT /api/accounts/<id:uuid> - сущность read only
* DELETE /api/accounts/<id:uuid> - сущность read only

Для транзакций организовано подобие event sourcing-а: транзакции накапливаются в append only хранилище, а баланс счета каждый раз высчитывается по истории транзакций.

Несмотря на то, что persistence идет в памяти в коде делается предположение, что обращение к нему идет через блокирующий API, поэтому вызовы оформлены соответствующим образом, чтобы не заблокировать event loop.

Из-за временных ограничений тестами покрыты основные сценарии:
* многопоточная работа с транзакциями на уровне модели данных (persistence.TransactionsTest)
* интеграционное прохождение сценария: создания пользователей, счетов и транзакции между ними (rest.TransactionsTest)
* также есть еще несколько вспомогательных тестов

Что дальше?
* Persistence и последующие вопросы конкурентного доступа к данным
    * Материализованное представление для баланса каждого счета, чтобы не нужно было его каждый раз высчитывать на лету
* Конфигурирование с помощью внешнего файла
* Устранить дублирование кода в Handler-ах
* Json
    * Нужно далее развивать идею с описанием типов в JsonType, в идеале там должна быть вся метаинформация по формату
    * Схема и тестирование каждой сущности по схеме
    * Обновление только изменившихся полей (merge)
    * Paging
    * Filtering
    * Нормальная обработка ошибок с выводом подробного описания в json-е
* Тесты
