# JSabbat
JSabbat is an application containing multiple functionalities. The idea is to build an distributed application with the ability to run on home servers to keep the data private BUIT with all advantages of
scalibilty and availability. Moreover it is a learning platform to learn stuff like:
* service oriented architecture
* event driven systems
* big data analysis
* "Microservices"

The following list decsribes the bounded contexts:Applications to be used in high available private clouds (like messenging, communitypay)

* messenger: allows all users to send messages to each other with different external interfaces (e.g. like email, mobile phone push, twitter) but also in the future websockets)
* location: an application to track geographic location data, heart rates, cadenc of road bike riders to keep track of them and to connect friends of the same community group with each other. So they shall know where friends are, what the distance is between them.


Technical facts:

system architecture

  |sabbat.api-gateway| <--> |RabbitMQ Broker| <--> |location.app| --> |sabbat.messenger|

