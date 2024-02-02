# springBoot

#### 

Описание: HouseHistory заполняется через 2 тригера.

Примеры запросов:

### House

1. GET getAllHouse

Запрос:
http://localhost:8080/houses?pageNumber=0&pageSize=2

Ответ:

[
{
"uuid": "99efee95-2f1c-459e-b97c-509f7399aa01",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-12 23:29:04.595"
},
{
"uuid": "d7d5c085-d470-4f4b-8d3c-f04a1305d4ea",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-13 01:26:12.411"
}
]

2. GET getByUUID

http://localhost:8080/houses/633677d4-32a4-4728-b298-bfd8bbae8445

Ответ:

{
"uuid": "633677d4-32a4-4728-b298-bfd8bbae8445",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-14 20:10:17.985"
}

4. getAllResidentsByHouse

Запрос: GET http://localhost:8080/houses/persons/99efee95-2f1c-459e-b97c-509f7399aa01

Ответ:

[
{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccffa9",
"name": "John",
"surname": "Doe",
"sex": "MALE",
"passportSeries": "HB",
"passportNumber": 3,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
}
]

4. addHouse

Запрос: POST http://localhost:8080/houses

{
    "area": "Some area",
    "country": "Some country",
    "city": "Some city",
    "street": "Some street",
    "number": 1234567
}

Ответ: "bb0a883e-1576-4b85-b94c-cd93c240e9f7"

5. addHouseOwner

Запрос: POST

http://localhost:8080/houses/owners?house=99efee95-2f1c-459e-b97c-509f7399aa01&person=d8b6eda1-2ac7-4190-8523-389b3cccff10

6. deleteHouse

Запрос: DEL

http://localhost:8080/houses/633677d4-32a4-4728-b298-bfd8bbae8445

7. updateHouse

Запрос: PUT

http://localhost:8080/houses/d7d5c085-d470-4f4b-8d3c-f04a1305d4ea

{
"area": "Gomelskay area",
"country": "Some country",
"city": "Gomel city",
"street": "Som street",
"number": 1234
}

Ответ:

Не возвращал. Но обновление происходит с оставлением старого ID, но перемещается в конец таблицы.

8. updateHouse Copy

Запрос: PATCH 

http://localhost:8080/houses?house=633677d4-32a4-4728-b298-bfd8bbae8445&person=374e13db-4bc9-41a4-a261-c3c3326622c9

{
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-14 20:10:17.985"
}

Ответ: 

{
"uuid": "d7d5c085-d470-4f4b-8d3c-f04a1305d4ea",
"area": "Some area111",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-13 01:26:12.411"
}

### Person

1. getAllPerson

Запрос: GET http://localhost:8080/persons?pageNumber=0&pageSize=2

Ответ:

[
{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccffa9",
"name": "John",
"surname": "Doe",
"sex": "MALE",
"passportSeries": "HB",
"passportNumber": 3,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
},
{
"uuid": "3cd20b71-6381-4658-9711-f834f1c3373a",
"name": "John",
"surname": "Doe",
"sex": "MALE",
"passportSeries": "HB",
"passportNumber": 1,
"createDate": "2024-01-13 01:26:12.411",
"updateDate": "2024-01-13 01:26:12.411"
}
]

2. getUUIDPerson

Запрос: GET http://localhost:8080/persons/d8b6eda1-2ac7-4190-8523-389b3cccff10

Ответ:

{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccff10",
"name": "Yury",
"surname": "Petrov",
"sex": "MALE",
"passportSeries": "HB",
"passportNumber": 5,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
}

3. GET getAllHousesByOwner

Запрос:

http://localhost:8080/persons/houses/d8b6eda1-2ac7-4190-8523-389b3cccffa9

Ответ:

[
{
"uuid": "99efee95-2f1c-459e-b97c-509f7399aa01",
"area": "Some area",
"country": "Some country",
"city": "Some city",
"street": "Some street",
"number": 123,
"createDate": "2024-01-12 23:29:04.595"
}
]

4. addPerson POST

Запрос: http://localhost:8080/persons/99efee95-2f1c-459e-b97c-509f7399aa01

{
"name": "Yury9",
"surname": "Petrov",
"sex": "MALE",
"passportSeries": "HB",
"passportNumber": 32
}

5. deletePerson DEL

http://localhost:8080/persons/47686e0c-ce10-40c4-b188-6f1e24434f59

Ответ:

Нет или 

{
"info": "Object not found UUID"
}

6. updatePerson PUT

Запрос:
http://localhost:8080/persons/d8b6eda1-2ac7-4190-8523-389b3cccffa9

{
"name": "Masha4",
"surname": "Петрова",
"sex": "FEMALE",
"passportSeries": "AA",
"passportNumber": 7
}

Ответ: 
Не возвращал. Но обновление происходит с оставлением старого ID, но перемещается в конец таблицы.

7. updatePerson Copy PATCH

Запрос: http://localhost:8080/persons?person=d8b6eda1-2ac7-4190-8523-389b3cccffa9&house=99efee95-2f1c-459e-b97c-719f7399aa25

{
"name": "Masha",
"surname": "Petrova1",
"sex": "MALE",
"passportSeries": "AA",
"passportNumber": 7,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-12 23:29:04.595"
}

Ответ: 

{
"uuid": "d8b6eda1-2ac7-4190-8523-389b3cccffa9",
"name": "Masha",
"surname": "Petrova1",
"sex": "MALE",
"passportSeries": "AA",
"passportNumber": 7,
"createDate": "2024-01-12 23:29:04.595",
"updateDate": "2024-01-26 03:49:43.641"
}
