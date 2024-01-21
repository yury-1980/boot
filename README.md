# hibernate

#### Подключить apache-tomcat-10.1.17

Примеры запросов:

### House

1. getAllHouse

Запрос:
   http://localhost:8080/houses?pageNumber=3&pageSize=1

Ответ:

[
{
"uuid": "633677d4-32a4-4728-b298-bfd8bbae8445",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-14 20:10:17.985"
}
]

2. getUUID

Запрос: http://localhost:8080/houses/99efee95-2f1c-459e-b97c-509f7399aa01

Ответ:

{
"uuid": "99efee95-2f1c-459e-b97c-509f7399aa01",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-12 23:29:04.595"
}

3. addHouse

Запрос: http://localhost:8080/houses

{
"uuid": "99efee96-2f1c-459e-b97c-609f7399aa03",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-12 23:29:04.595"
}

Ответ: "99efee96-2f1c-459e-b97c-609f7399aa03"

4. addHouse Copy

Запрос: http://localhost:8080/houses

{
"uuid": "633677d4-32a4-4728-b298-bfd8bbae8445",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-14 20:10:17.985",
"ownerList": [
{
"uuid": "f4036a98-9fe6-4b85-bf89-3342807872c2",
"name": "John",
"surname": "Doe",
"sex": "Male",
"passportSeries": "AB",
"passportNumber": 123456,
"createDate": "2024-01-14 20:10:17.988",
"updateDate": "2024-01-14 20:10:17.988",
"house": {
"uuid": "99efee95-2f1c-459e-b97c-519f7399aa03",
"area": "Homelska area",
"country": "Belarus country",
"city": "Homel city",
"street": "Sovet street",
"number": 123,
"createDate": "2024-01-12 23:29:04.595"
}
}
]
}

Ответ: "633677d4-32a4-4728-b298-bfd8bbae8455"

5. deleteHouse

Запрос:

http://localhost:8080/houses/633677d4-31a4-4728-b298-bfd8bbae8446 

6. updateHouse

Запрос: http://localhost:8080/houses/99efee95-2f1c-459e-b97c-509f7399aa01

{
"uuid": "99efee95-2f1c-459e-b97c-509f7399aa01",
"area": "Gomelskay area",
"country": "Some country",
"city": "Gomel city",
"street": "Some street",
"number": 10,
"createDate": "2024-01-12 23:29:04.595"
}

Ответ:

Не возвращал. Но обновление происходит с оставлением старого ID, но перемещается в конец таблицы.

### Person

1. getAllPerson

Запрос: http://localhost:8080/persons?pageNumber=1&pageSize=3

Ответ:

[
{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccffa9",
"name": "John",
"surname": "Doe",
"sex": "Male",
"passportSeries": "HB",
"passportNumber": 3,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
},
{
"uuid": "3cd20b71-6381-4658-9711-f834f1c3373a",
"name": "John",
"surname": "Doe",
"sex": "Male",
"passportSeries": "HB",
"passportNumber": 1,
"createDate": "2024-01-13 01:26:12.411",
"updateDate": "2024-01-13 01:26:12.411"
},
{
"uuid": "f4036a98-9fe6-4b85-bf89-3342807872c2",
"name": "John",
"surname": "Doe",
"sex": "Male",
"passportSeries": "AB",
"passportNumber": 123456,
"createDate": "2024-01-14 20:10:17.988",
"updateDate": "2024-01-14 20:10:17.988"
}
]

2. getUUIDPerson

Запрос: http://localhost:8080/persons/d8b6eda1-2ac7-4190-8523-389b3cccffa9

Ответ:
{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccffa9",
"name": "John",
"surname": "Doe",
"sex": "Male",
"passportSeries": "HB",
"passportNumber": 3,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
}

3. addPerson

Запрос: http://localhost:8080/persons

Ответ:

{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccff11",
"name": "Yury",
"surname": "Petrov",
"sex": "Male",
"passportSeries": "HB",
"passportNumber": 5,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595",
"house": {
"uuid": "99efee95-2f1c-459e-b97c-509f7399aa03",
"area": "Homelska area",
"country": "Belarus country",
"city": "Homel city",
"street": "Sovet street",
"number": 123,
"createDate": "2024-01-12 23:29:04.595"
}
}

4. deletePerson

Запрос: http://localhost:8080/houses/633677d4-31a4-4728-b298-bfd8bbae8446

5. updatePerson

Запрос:
http://localhost:8080/persons/d8b6eda1-2ac7-4190-8523-389b3cccffa9

{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccffa9",
"name": "Masha",
"surname": "Petrova",
"sex": "Male",
"passportSeries": "AA",
"passportNumber": 3,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
}

Ответ: 
Не возвращал. Но обновление происходит с оставлением старого ID, но перемещается в конец таблицы.