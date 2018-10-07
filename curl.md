GetAll
curl -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meal

Get
curl -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meal/100002

Удаление
curl -i -X DELETE -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meal/100003

Создание
curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meal -d '{"dateTime":"2015-05-30T15:00:00","description":"новая еда","calories":4000}'

Изменение
curl -i -X PUT -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meal/100002 -d '{"id":100002,"dateTime":"2015-05-30T13:30:00","description":"измененная еда","calories":4000}'


Фильтр
curl -i -H "Content-Type:application/json" http://localhost:8080/topjava/rest/meal/filterAll