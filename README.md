# Weather-Project

connection of two servers is established by RabbitMQ
#########################################################################
you can call apis directly from server1 but using Gateway is optional
#########################################################################

post api : http://localhost:9090/api/v1/addCity

body : {
     "name": "tehran",
     "lat":" sdasdas",
     "lon":" asdasdas"
}

Basic Auth for postman:

username = user
password = password

#########################################################################
http://localhost:9090/api/v1/getWeather/tehran
Basic Auth for postman:

username = user
password = password


#########################################################################

weather fetches from https://rapidapi.com/weatherapi/api/weatherapi-com

due to a connection loss server1 send an email to the defined email 

